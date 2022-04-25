package sistAlarmas;

public class Admin {
	
	/* Se mantiene una referencia al usuario al que "pertenece" esta admin. El admin se
	 * identifica a través de su usuario. 
	 * Podría considerarse la relación usuario-admin como la existente entre un todo-parte.
	 */
	private UsuarioRegistrado usuario;

	/* A falta de una interfaz grafica o un sistema de autenticacion donde el uso de esta clase
	 * sea completamente necesario, queda a modo de contenedor.
	 * Se incluye dentro de este incremento tanto por completitud a la hora de la representacion
	 * de una jerarquia de usuarios como para cumplir con los requerimientos de informacion.
	 */
	
	//Constructor
	public Admin() {}

	//Metodo para establecer un usuario como administrador
	public Admin setUsuarioRegistrado(UsuarioRegistrado usuarioR) throws Exception {
		if (this.usuario != null) throw new Exception("El administrador ya estaba vinculado a un perfil de usuario");
		if (usuarioR == null) throw new Exception("Usuario no valido");
		this.usuario = usuarioR;
		return this;
	}
}
