// index.js
import React from 'react';
import ReactDOM from 'react-dom/client'; // Asegúrate de importar desde 'react-dom/client'
import App from './App';
import { AuthProvider } from './contexts/AuthContext'; // Si usas un AuthContext

const root = ReactDOM.createRoot(document.getElementById('root')); // Usamos createRoot
root.render(
  <AuthProvider>
    <App />
  </AuthProvider>
);
