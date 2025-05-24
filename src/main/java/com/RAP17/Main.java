package com.RAP17;

/*import com.RAP17.Usuarios;*/
import org.hibernate.cfg.Configuration;
import org.hibernate.*;

public class Main {
    public static void main(String[] args) {
   // 1. Crear una SessionFactory a partir del archivo de configuración de Hibernate
        // Esto se hace una vez por aplicación
        SessionFactory factory = new Configuration()
                                    .configure("hibernate.cfg.xml") // Carga la configuración del XML
                                    .addAnnotatedClass(Usuarios.class) 
                                    .buildSessionFactory();
 
        Session session = null;
        Transaction transaction = null; 
 
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Usuarios nuevoUsuario = new Usuarios("LuisaR2", "prueba123", "Luisa Rubio", "juan.perez@example.com", "Operador", "preventa");
 
            // 5. Guardar el objeto en la base de datos (realiza el INSERT)
            // Hibernate insertará este objeto como una nueva fila en la tabla 'usuarios'
            session.persist(nuevoUsuario); // El método 'save' se usa para nuevas entidades
 
            // 6. Confirmar la transacción
            transaction.commit();
 
            System.out.println("Usuario insertado exitosamente con ID: " + nuevoUsuario.getId());
 
        } catch (Exception e) {
            // Manejar errores: revertir la transacción si algo falla
            if (transaction != null) {
                transaction.rollback();
                System.err.println("La transacción fue revertida debido a un error.");
            }
            System.err.println("Error al insertar usuario: " + e.getMessage());
            e.printStackTrace(); // Imprime la traza completa del error para depuración
        } finally {
            // 7. Cerrar la sesión y la SessionFactory
            if (session != null) {
                session.close();
            }
            if (factory != null) {
                factory.close();
            }
        }
    }
}