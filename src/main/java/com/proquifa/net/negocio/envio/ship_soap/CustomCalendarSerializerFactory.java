package com.proquifa.net.negocio.envio.ship_soap;

import javax.xml.namespace.QName;
import org.apache.axis.encoding.ser.*;

public class CustomCalendarSerializerFactory extends BaseSerializerFactory {
    public CustomCalendarSerializerFactory(Class javaType, QName xmlType) {
        super(CustomCalendarSerializer.class, xmlType, javaType);
    }
}
