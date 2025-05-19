/**
 * ShipPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.proquifa.net.negocio.envio.ship.stub;

public interface ShipPortType extends java.rmi.Remote {
    public com.proquifa.net.negocio.envio.ship.stub.ProcessTagReply processTag(com.proquifa.net.negocio.envio.ship.stub.ProcessTagRequest processTagRequest) throws java.rmi.RemoteException;
    public com.proquifa.net.negocio.envio.ship.stub.ProcessShipmentReply processShipment(com.proquifa.net.negocio.envio.ship.stub.ProcessShipmentRequest processShipmentRequest) throws java.rmi.RemoteException;
    public com.proquifa.net.negocio.envio.ship.stub.ShipmentReply deleteTag(com.proquifa.net.negocio.envio.ship.stub.DeleteTagRequest deleteTagRequest) throws java.rmi.RemoteException;
    public com.proquifa.net.negocio.envio.ship.stub.ShipmentReply deleteShipment(com.proquifa.net.negocio.envio.ship.stub.DeleteShipmentRequest deleteShipmentRequest) throws java.rmi.RemoteException;
    public com.proquifa.net.negocio.envio.ship.stub.ShipmentReply validateShipment(com.proquifa.net.negocio.envio.ship.stub.ValidateShipmentRequest validateShipmentRequest) throws java.rmi.RemoteException;
}
