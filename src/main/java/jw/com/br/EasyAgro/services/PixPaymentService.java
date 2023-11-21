package jw.com.br.EasyAgro.services;


import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import jw.com.br.EasyAgro.domain.order.Order;
import jw.com.br.EasyAgro.domain.order.OrderProduct;
import jw.com.br.EasyAgro.domain.user.User;
import jw.com.br.EasyAgro.dtos.OrderDTO;
import jw.com.br.EasyAgro.dtos.mercadopago.PixPaymentDTO;
import jw.com.br.EasyAgro.dtos.mercadopago.PixPaymentResponseDTO;
import jw.com.br.EasyAgro.exception.MercadoPagoException;
import jw.com.br.EasyAgro.repositories.OrderRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PixPaymentService {
    //@Value("${mercado_pago_sample_access_token}")
    private String mercadoPagoAccessToken = "APP_USR-474140450138612-112019-09ae90f0cc7758aaa68ce5bfd375e4b1-130665105";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public PixPaymentResponseDTO processPayment(PixPaymentDTO pixPaymentDTO) {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            PaymentClient paymentClient = new PaymentClient();

            PaymentCreateRequest paymentCreateRequest =
                    PaymentCreateRequest.builder()
                            .transactionAmount(pixPaymentDTO.getTransactionAmount())
                            .description(pixPaymentDTO.getProductDescription())
                            .paymentMethodId("pix")
                            .payer(
                                    PaymentPayerRequest.builder()
                                            .email(pixPaymentDTO.getPayer().getEmail())
                                            .firstName(pixPaymentDTO.getPayer().getFirstName())
                                            .lastName(pixPaymentDTO.getPayer().getLastName())
                                            .identification(
                                                    IdentificationRequest.builder()
                                                            .type(pixPaymentDTO.getPayer().getIdentification().getType())
                                                            .number(pixPaymentDTO.getPayer().getIdentification().getNumber())
                                                            .build())
                                            .build())
                            .build();



            Payment createdPayment = paymentClient.create(paymentCreateRequest);




            Order order = new Order(
                    pixPaymentDTO.getTransactionAmount(),
                    createdPayment.getStatus(),
                    pixPaymentDTO.getOrders(),
                    String.valueOf(createdPayment.getId()));
            orderRepository.insert(order);

            return new PixPaymentResponseDTO(
                    createdPayment.getId(),
                    String.valueOf(createdPayment.getStatus()),
                    createdPayment.getStatusDetail(),
                    createdPayment.getPointOfInteraction().getTransactionData().getQrCodeBase64(),
                    createdPayment.getPointOfInteraction().getTransactionData().getQrCode());
        } catch (MPApiException apiException) {
            System.out.println(apiException.getApiResponse().getContent());
            throw new MercadoPagoException(apiException.getApiResponse().getContent());
        } catch (MPException exception) {
            System.out.println(exception.getMessage());
            throw new MercadoPagoException(exception.getMessage());
        }
    }

    public void updateStatusPayment(String id) {
        try {
            MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);

            PaymentClient paymentClient = new PaymentClient();
            Payment createdPayment = paymentClient.get(Long.valueOf(id));

            if (createdPayment != null) {
                String status = String.valueOf(createdPayment.getStatus());
                Query query = new Query(Criteria.where("transitionId").is(id));
                Update update = new Update().set("status", status);

                mongoTemplate.updateFirst(query, update, Order.class);
            } else {
                System.out.println("Pagamento não encontrado para o ID: " + id);
            }
        } catch (MPApiException apiException) {
            System.out.println(apiException.getApiResponse().getContent());
            throw new MercadoPagoException(apiException.getApiResponse().getContent());
        } catch (MPException exception) {
            System.out.println(exception.getMessage());
            throw new MercadoPagoException(exception.getMessage());
        }
    }

}