import React, { createContext, useState, useContext } from 'react';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null); // Guardamos la información del usuario
  
  const login = (userData) => {
    setUser(userData); // Guardamos el token y los datos del usuario
  };

  const logout = () => {
    setUser(null); // Limpiamos los datos del usuario al hacer logout
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext); // Hook para consumir el contexto
