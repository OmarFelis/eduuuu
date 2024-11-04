package modelo;

import java.io.Serializable;

public class Empleado implements Serializable
{
    int codigo;     // 4 bytes
    // 2 bytes X caracter, asumo usar 30 caracteres por nombre
    String nombre;  // 60 bytes
    double sueldo;  // 8 bytes -->  total = 72 bytes

    public Empleado() 
    {
    }

    public Empleado(int codigo, String nombre, double sueldo) 
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sueldo = sueldo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public int getTam()
    {
        return 14;
    }
   
}
