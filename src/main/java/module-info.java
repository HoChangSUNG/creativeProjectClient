module com.creative.creativeprojectclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires java.sql;

    opens com.creative.creativeprojectclient to javafx.fxml;
    exports com.creative.creativeprojectclient;
}