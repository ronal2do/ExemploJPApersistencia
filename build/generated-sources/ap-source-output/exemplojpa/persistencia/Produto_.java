package exemplojpa.persistencia;

import exemplojpa.persistencia.Venda;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-04-01T10:10:53")
@StaticMetamodel(Produto.class)
public class Produto_ { 

    public static volatile SingularAttribute<Produto, String> nome;
    public static volatile SingularAttribute<Produto, Integer> id;
    public static volatile CollectionAttribute<Produto, Venda> vendaCollection;

}