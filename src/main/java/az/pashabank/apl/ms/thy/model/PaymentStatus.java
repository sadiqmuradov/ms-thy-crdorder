/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.thy.model;

public class PaymentStatus {

    private int id;
    private String status;

    public PaymentStatus() {
    }

    public PaymentStatus(int id) {
        this.id = id;
    }

    public PaymentStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "PaymentStatus{" + "id=" + id + ", status=" + status + '}';
    }

}
