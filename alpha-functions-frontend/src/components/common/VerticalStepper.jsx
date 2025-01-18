import React from 'react';
import { Box, Step, StepContent, StepLabel, Stepper, Typography } from '@mui/material';
import CustomStepIcon from './CustomStepIcon';

function VerticalStepper({ activeStep, steps }) {
  return (
    <Box sx={{ margin: 'auto', width: '100% !important', minHeight: '50vh' }}>
      <Stepper activeStep={activeStep} orientation="vertical">
        {steps.map((step, index) => (
          <Step key={step.label} style={{ marginLeft: '4px', border: '20px' }}>
            <StepLabel StepIconComponent={CustomStepIcon}>
              <Box>
                <Typography style={{ fontSize: '12px' }}>
                  Step {index + 1}
                </Typography>
                <Typography style={{ fontSize: '13px', fontWeight: 'normal' }}>{step.label}</Typography>
              </Box>
            </StepLabel>
            <StepContent>
              <Typography>{step.description}</Typography>
            </StepContent>
          </Step>
        ))}
      </Stepper>
    </Box>
  );
}

export default VerticalStepper;