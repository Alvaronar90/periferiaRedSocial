import React, { useState } from "react";
import "../../Style/StylleLogis.css";
import { Formik, Field, Form } from "formik";
import * as Yup from "yup";
import Swal from "sweetalert2";
import axios from "axios";
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext'; // Importamos el contexto de autenticación

// Validación de Yup
const loginValidationSchema = Yup.object({
	username: Yup.string().email("Correo electrónico inválido"),
	password: Yup.string().required("La contraseña es obligatoria"),
});

const registerValidationSchema = Yup.object({
	name: Yup.string().required("El nombre es obligatorio"),
	email: Yup.string()
		.email("Correo electrónico inválido")
		.required("El email es obligatorio"),
	password: Yup.string().required("La contraseña es obligatoria"),
	confirmPassword: Yup.string()
		.oneOf([Yup.ref("password"), null], "Las contraseñas deben coincidir")
		.required("La confirmación de la contraseña es obligatoria"),
});

function Componente1() {
	// Estado para manejar el cambio entre el formulario de login y el de registro
	const [isRegistering, setIsRegistering] = useState(false);
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const { login } = useAuth(); // Obtenemos la función login del contexto
  const navigate = useNavigate();

	// Manejadores de eventos
	const handleRegisterClick = () => setIsRegistering(true);
	const handleLoginClick = () => setIsRegistering(false);

	// Enviar formulario de registro
	const handleRegisterSubmit = async (values) => {
		// Lógica para registrar usuario, enviar datos con Axios, etc.
		const config = {
			headers: {
				"Content-Type": "application/json",
				// Authorization: `Bearer ${getToken()}`,
			},
		};
		return axios
			.post("http://localhost:8080/auth/register", values, config)
			.then((response) => {
				if (response.status === 201) {
					Swal.fire({
						icon: "success",
						title: "Usuario registrado",
						showConfirmButton: false,
						timer: 1500,
					}).then(() => {
						// Limpiar los campos del formulario
						Formik.resetForm();
					});
				} else {
					Swal.fire({
						icon: "warning",
						title: "Error",
						text: "Error al guardar usuario",
						showConfirmButton: false,
						timer: 1500,
					});
				}
			})
			.catch((error) => {
				const errorMessage = error?.response?.data || "Error desconocido"; // Si no hay mensaje, usamos un valor por defecto

				Swal.fire({
					icon: "warning",
					title: "Error",
					text: errorMessage,
					showConfirmButton: false,
					timer: 1500,
				});
			});

		//----------------------------------------------------------
	};

	// Enviar formulario de login
	const handleLoginSubmit = (values) => {
		// Lógica para registrar usuario, enviar datos con Axios, etc.
		const config = {
			headers: {
				"Content-Type": "application/json",
				// Authorization: `Bearer ${getToken()}`,
			},
		};
		return axios
			.post("http://localhost:8080/auth/login", values, config)
			.then((response) => {
        console.log(response)
				if (response.status === 200) {
					Swal.fire({
						icon: "success",
						title: "Ingreso exitoso",
						showConfirmButton: false,
						timer: 1500,
					}).then(() => {
						 // Guardamos el token y la información del usuario en el contextoresponse.data
             console.log(response.data)
             const { token, name, email,id } = response.data;
             login({ token, name, email,id });
   
             // Redirigimos al componente principal
             navigate('/principal');
						
					});
				} else {
					Swal.fire({
						icon: "warning",
						title: "Error",
						text: "Credeciales invalidas",
						showConfirmButton: false,
						timer: 1500,
					});
				}
			})
			.catch((error) => {
				const errorMessage = error?.response?.data || "Error desconocido"; // Si no hay mensaje, usamos un valor por defecto
				Swal.fire({
					icon: "warning",
					title: "Error",
					text: errorMessage.message,
					showConfirmButton: false,
					timer: 1500,
				});
			});
	};

	return (
		<div className="container">
			{/* Header */}
			<header className="header">
				<h1>PERIFERIA NETWORK</h1>
			</header>

			{/* Body */}
			<div className="body">
				{/* Sección izquierda: 70% */}
				<div className="left-section">
					<div className="card">
						<img
							src="https://via.placeholder.com/150"
							alt="Periferia"
							className="card-image"
						/>
						<h2>PERIFERIA</h2>
						<p>Una red para conectar a todos.</p>
					</div>
				</div>

				{/* Sección derecha: 30% */}
				<div className="right-section">
					{/* Formulario de Login o Registro */}
					{isRegistering ? (
						<Formik
							initialValues={{
								name: "",
								email: "",
								password: "",
								confirmPassword: "",
							}}
							validationSchema={registerValidationSchema}
							onSubmit={handleRegisterSubmit}
						>
							{({ errors, touched }) => (
								<Form className="login-form">
									<label htmlFor="name">Nombre</label>
									<Field type="text" id="name" name="name" required />
									{errors.name && touched.name && (
										<div className="error">{errors.name}</div>
									)}

									<label htmlFor="email">Email</label>
									<Field type="email" id="email" name="email" required />
									{errors.email && touched.email && (
										<div className="error">{errors.email}</div>
									)}

									<label htmlFor="password">Contraseña</label>
									<Field
										type="password"
										id="password"
										name="password"
										required
									/>
									{errors.password && touched.password && (
										<div className="error">{errors.password}</div>
									)}

									<label htmlFor="confirmPassword">Verificar Contraseña</label>
									<Field
										type="password"
										id="confirmPassword"
										name="confirmPassword"
										required
									/>
									{errors.confirmPassword && touched.confirmPassword && (
										<div className="error">{errors.confirmPassword}</div>
									)}

									<button type="submit" className="btn-register">
										Registrar
									</button>
									<button
										type="button"
										className="btn-login"
										onClick={handleLoginClick}
									>
										Ingresar
									</button>
								</Form>
							)}
						</Formik>
					) : (
						<Formik
							initialValues={{ email: "", password: "" }}
							validationSchema={loginValidationSchema}
							onSubmit={handleLoginSubmit}
						>
							{({ errors, touched }) => (
								<Form className="login-form">
									<label htmlFor="username">Usuario</label>
									<Field type="text" id="email" name="email" required />
									{errors.email && touched.email && (
										<div className="error">{errors.email}</div>
									)}

									<label htmlFor="password">Contraseña</label>
									<Field
										type="password"
										id="password"
										name="password"
										required
									/>
									{errors.password && touched.password && (
										<div className="error">{errors.password}</div>
									)}

									<button type="submit" className="btn-login">
										Ingresar1
									</button>
									<button
										type="button"
										className="btn-register"
										onClick={handleRegisterClick}
									>
										Registrar
									</button>
								</Form>
							)}
						</Formik>
					)}
				</div>
			</div>

			{/* Footer */}
			<footer className="footer">
				<p>&copy; 2025 Periferia Network. Todos los derechos reservados.</p>
			</footer>
		</div>
	);
}

export default Componente1;
