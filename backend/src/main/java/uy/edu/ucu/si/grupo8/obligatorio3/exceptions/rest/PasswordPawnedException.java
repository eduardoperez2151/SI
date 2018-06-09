package uy.edu.ucu.si.grupo8.obligatorio3.exceptions.rest;

public class PasswordPawnedException extends Exception {

    public PasswordPawnedException() {
        super("La contraseña sido hackeada, por favor re-ingrese otra");
    }
}
