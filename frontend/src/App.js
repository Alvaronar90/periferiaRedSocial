import React, { useContext } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { AuthContext } from './contexts/AuthContext'; // Importamos el contexto
import Componente1 from './components/home/login.jsx'; // Tu componente de login
import ComponentePrincipal from './components/home/MuroEntrada.jsx'; // Tu componente principal

function App() {
  const { user } = useContext(AuthContext); // Usamos el contexto para verificar si el usuario está autenticado

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} /> {/* Redirige a login por defecto */}
        
        {/* Ruta de login */}
        <Route path="/login" element={!user ? <Componente1 /> : <Navigate to="/principal" />} />
        
        {/* Ruta principal protegida */}
        <Route
          path="/principal"
          element={user ? <ComponentePrincipal /> : <Navigate to="/login" />} // Si no está autenticado, redirige al login
        />
      </Routes>
    </Router>
  );
}

export default App;

