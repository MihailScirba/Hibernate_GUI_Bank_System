module com.bank.guibank {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;
    requires org.jetbrains.annotations;


    opens com.bank.guibank.model.user to org.hibernate.orm.core;
    opens com.bank.guibank.model.transactions to org.hibernate.orm.core;
    opens com.bank.guibank.view to javafx.fxml;
    opens com.bank.guibank.controller to javafx.fxml;
    opens com.bank.guibank.controller.user to javafx.fxml, javafx.base;
    opens com.bank.guibank.controller.user.utils to javafx.base, javafx.fxml;
    opens com.bank.guibank.util to javafx.fxml;
    exports com.bank.guibank.app;
}