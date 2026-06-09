module com.ed522.svgmassconvert {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires com.github.weisj.jsvg;


    opens com.ed522.svgmassconvert to javafx.fxml;
    exports com.ed522.svgmassconvert;
}