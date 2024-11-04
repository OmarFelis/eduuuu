package control;

import modelo.Empleado;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AccessFile 
{
    RandomAccessFile flujo;
    int numRegistros;
    int tamRegistro = 72;  // 72 bytes
    
    public void abrirFile(String archi) throws IOException
    {
        flujo = new RandomAccessFile(archi,"rw");
        // ceil redondea al entero superior : ceil(2.9) --> 2, ceil(2.1) --> 2 
        numRegistros = (int)Math.ceil((double)flujo.length()/(double)tamRegistro);
    }   
    
    public void cerrarFile() throws IOException
    {
        flujo.close();
    }
    
    public int getNumRegistros()
    {
        return numRegistros;
    }
    
    public void agregarEmple(Empleado emp) throws IOException
    {
        flujo.seek(numRegistros*tamRegistro);
        flujo.writeInt(emp.getCodigo());
        flujo.writeUTF(emp.getNombre());
        flujo.writeDouble(emp.getSueldo());
        numRegistros++;
  
    }

    public Empleado obtenerUnEmpleado(int i) throws IOException
    {
            flujo.seek(i*tamRegistro);
            return new Empleado(flujo.readInt(), 
                                flujo.readUTF().trim(),
                                flujo.readDouble()
                                );
    }
    
    public Empleado buscarEmpleado(int codi) throws IOException
    {
        Empleado e = null;
        for(int i=0; i<getNumRegistros(); ++i)
        {
            flujo.seek(i*tamRegistro);
            e = obtenerUnEmpleado(i);
            int codigo = e.getCodigo();
            if(codigo == codi)
                return e;
        }
        return null;
    }
    
    public String getEmpleados() throws IOException
    {
        String datEmp = "";
        for(int i=0; i<getNumRegistros(); ++i)
        {
            flujo.seek(i*tamRegistro);
            Empleado e = obtenerUnEmpleado(i);
            datEmp = datEmp +   e.getCodigo() + "\t" +
                                e.getNombre() + "\t\t" +
                                e.getSueldo() + "\n";   
        }
        return datEmp;
    }
    // Método para borrar un empleado
    public boolean borrarEmpleado(int codigo) throws IOException {
        for (int i = 0; i < getNumRegistros(); ++i) {
            flujo.seek(i * tamRegistro);
            Empleado e = obtenerUnEmpleado(i);
            if (e.getCodigo() == codigo) {
                for (int j = i; j < numRegistros - 1; j++) {
                    flujo.seek((j + 1) * tamRegistro);
                    Empleado siguiente = obtenerUnEmpleado(j + 1);
                    flujo.seek(j * tamRegistro);
                    flujo.writeInt(siguiente.getCodigo());
                    flujo.writeUTF(siguiente.getNombre());
                    flujo.writeDouble(siguiente.getSueldo());
                }
                flujo.setLength((numRegistros - 1) * tamRegistro);
                numRegistros--;
                return true;
            }
        }
        return false; // Si el empleado no se encuentra
    }
}









