package org.informatics.service;

import org.informatics.dao.ClientDao;
import org.informatics.dao.EmployeeDao;
import org.informatics.dao.ShipmentDao;
import org.informatics.entity.Shipment;
import org.informatics.logistic.dao.*;
import org.informatics.logistic.entity.*;

import java.util.List;

public class ReportService {

    private final ShipmentDao shipmentRepo = new ShipmentDao();
    private final EmployeeDao employeeRepo = new EmployeeDao();
    private final ClientDao clientRepo = new ClientDao();

    public List<Employee> allEmployees() {
        return employeeRepo.findAll();
    }

    public List<Client> allClients() {
        return clientRepo.findAll();
    }

    public List<Shipment> allShipments() {
        return shipmentRepo.findAll();
    }

    public List<Shipment> shipmentsByEmployee(Long employeeId) {
        return shipmentRepo.findByEmployee(employeeId);
    }

    public List<Shipment> shipmentsByClient(Long clientId) {
        return shipmentRepo.findByClient(clientId);
    }

    public List<Shipment> sentNotReceived() {
        return shipmentRepo.findSentNotReceived();
    }

    public Double revenue() {
        Double r = shipmentRepo.totalRevenue();
        return r == null ? 0.0 : r;
    }
}
