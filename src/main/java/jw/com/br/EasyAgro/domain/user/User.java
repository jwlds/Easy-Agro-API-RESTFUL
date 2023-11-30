package jw.com.br.EasyAgro.domain.user;

import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.domain.user.my.*;
import jw.com.br.EasyAgro.dtos.UserDTO;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User implements UserDetails {
    @Id
    private String id;
    private String login;
    private List<Cart> myCart;
    private List<MyProducts> myAdverts;
    private List<MyShopping> myShopping;
    private List<Favorites> myFavorites;
    private List<Task> myTask;
    private String nickname;
    private String cpf;
    private String password;
    private String phoneNumber;
    private String name;

    public User(UserDTO user) {
        this.nickname = user.nickname();
        this.login = user.login();
        this.password = user.password();
        this.phoneNumber = user.phoneNumber();
        this.cpf = user.cpf();
        this.name = user.name();
        this.myCart = new ArrayList<>();
        this.myAdverts = new ArrayList<>();
        this.myShopping = new ArrayList<>();
        this.myFavorites = new ArrayList<>();
        this.myTask = new ArrayList<>();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
