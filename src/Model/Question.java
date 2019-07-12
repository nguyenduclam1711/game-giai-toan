/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;

public class Question implements Serializable{
    
    private int a;
    private int b;
    private int operation;  // 0 = cong, 1 = tru, 2 = nhan, 3 = chia
    private int result;

    public Question(int a, int b, int operation, int result) {
        this.a = a;
        this.b = b;
        this.operation = operation;
        this.result = result;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Question{" + "a=" + a + ", b=" + b + ", operation=" + operation + ", result=" + result + '}';
    }
}
