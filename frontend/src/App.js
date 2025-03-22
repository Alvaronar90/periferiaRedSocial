import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Componente1 from './components/home/login.jsx'; // Asegúrate de que esté importado correctamente

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/componente1" />} /> {/* Redirige a Componente1 al iniciar */}
        <Route path="/componente1" element={<Componente1 />} />
      </Routes>
    </Router>
  );
}

export default App;
