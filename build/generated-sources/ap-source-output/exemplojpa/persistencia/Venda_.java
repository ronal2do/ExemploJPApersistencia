package exemplojpa.persistencia;

import exemplojpa.persistencia.Produto;
import exemplojpa.persistencia.Vendedor;
import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-01T10:10:53")
@StaticMetamodel(Venda.class)
public class Venda_ { 

    public static volatile SingularAttribute<Venda, Vendedor> vendedor;
    public static volatile SingularAttribute<Venda, Produto> produto;
    public static volatile SingularAttribute<Venda, BigDecimal> valorvenda;
    public static volatile SingularAttribute<Venda, Integer> id;

}