import { Box, Button } from '@mui/material';
import React from 'react';

const Controls = ({ activeStep, handleBack, handleNext, steps, handleReset, handleFinish }) => {
  return (
    <Box sx={{ width: '100%', display: 'flex', justifyContent: 'flex-end', marginTop: 2, position: 'fixed', bottom: 80, left: -170, right: 50, padding: 2 }}>
      <Button
        color="inherit"
        disabled={activeStep === 0}
        onClick={handleBack}
        sx={{ mr: 1 }}
      >
        Back
      </Button>
      <Button onClick={activeStep === steps.length - 1 ? handleFinish : handleNext} style={{backgroundColor: 'rgb(233, 145, 3)', color: 'white', padding: '10px 20px', borderRadius: '5px'}}>
        {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
      </Button>
    </Box>
  );
};

export default Controls;