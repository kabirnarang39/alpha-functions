import React from 'react';
import './Navbar.css';
import { Box } from '@mui/material';
import { Code } from '@mui/icons-material';
import { useHistory } from 'react-router-dom';

const Navbar = () => {
  const history = useHistory();  // Initialize the history function

  const handleRedirect = () => {
    history.push('/');  // Redirect to the home page
  };

  return (
    <nav className="navbar">
      <Box sx={{ display: 'flex', alignItems: 'center', paddingLeft: '15px' }} onClick={handleRedirect}>
        <Code />
      </Box>
    </nav>
  );
};

export default Navbar;
