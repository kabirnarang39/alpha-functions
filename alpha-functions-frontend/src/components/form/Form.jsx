import React, { useState, useEffect } from 'react';
import PropTypes from 'prop-types';
import { TextField, Select, MenuItem, FormControl, Checkbox, FormControlLabel, Box, RadioGroup, Radio, FormLabel, Typography, Grid, Button } from '@mui/material';
import './Form.css';
import { Add, AddAPhoto, AddBox, AddIcCallOutlined, Remove, RemoveCircleOutline } from '@mui/icons-material';

const commonStyles = {
  width: '100%',
  height: '40px',
  fontFamily: 'var(--font-family-base-4om3hr, "Amazon Ember", "Helvetica Neue", Roboto, Arial, sans-serif)',
  color: '#16191f',
  backgroundColor: '#ffffff',
  borderRadius: '2px',
  fontSize: '12px',
  "& input::placeholder": {
    fontSize: "14px",
    color: "rgb(15, 20, 26)"
  }
};

const initializeFormData = (fields) => {
  const initialData = {};
  fields.forEach(field => {
    if (field.props && field.props.defaultValue) {
      initialData[field.props.name] = field.props.defaultValue;
    }
    if (field.type === 'group' || field.type === 'form' || field.type === 'conditional') {
      Object.assign(initialData, initializeFormData(field.fields));
    }
  });
  return initialData;
};

const renderField = (field, index, handleChange, formData) => {
  const fieldStyles = field.styles || {};
  switch (field.type) {
    case 'heading':
      return (
        <Box key={index} sx={{ ...fieldStyles, width: '95%', margin: 'auto', justifyContent: 'center', fontWeight: 'bold' }}>
          <Typography key={index} variant="h6" sx={{ marginTop: 2, marginBottom: 1, color: 'var(--awsui-color-grey-600)', maxWidth: '95%', display: 'flex', justifyContent: 'flex-start', fontWeight: 'bold', fontSize: '18px' }}>
            {field.label}
          </Typography>
        </Box>
      );
    case 'text':
      return (
        <Box key={index} sx={{ ...fieldStyles, display: 'flex', justifyContent: 'center', width: '100%', flexDirection: 'row', flexWrap: 'nowrap' }}>
          <FormControl fullWidth margin="normal" component="fieldset" sx={{ maxWidth: '95.8%' }}>
            <FormLabel style={{
              boxSizing: 'border-box',
              color: 'var(--color-text-form-label-ttmnb4, #16191f)',
              display: 'inline',
              fontSize: 'var(--font-size-body-m-pa3mqb, 14px)',
              lineHeight: 'var(--line-height-body-m-2zx78l, 22px)',
              marginInlineEnd: 'var(--space-xs-xf5ch3, 8px)',
              marginLeft: '5px',
              fontWeight: 'bold',
              fontSize: '14px'
            }}>{field.label}</FormLabel>
            <TextField
              variant="outlined"
              margin="normal"
              {...field.props}
              sx={{ ...commonStyles, ...fieldStyles }}
              size='small'
              onChange={(e) => handleChange(field.props.name, e.target.value)}
              FormHelperTextProps={{ style: { fontSize: '10px', marginLeft: '0px' } }}
            />
          </FormControl>
        </Box>
      );
    case 'file': 
    return (<Box key={index} sx={{ ...fieldStyles, display: 'flex', justifyContent: 'center', width: '100%', flexDirection: 'row', flexWrap: 'nowrap' }}>
      <FormControl fullWidth margin="normal" component="fieldset" sx={{ maxWidth: '95.8%' }}>
        <FormLabel style={{
          boxSizing: 'border-box',
          color: 'var(--color-text-form-label-ttmnb4, #16191f)',
          display: 'inline',
          fontSize: 'var(--font-size-body-m-pa3mqb, 14px)',
          lineHeight: 'var(--line-height-body-m-2zx78l, 22px)',
          marginInlineEnd: 'var(--space-xs-xf5ch3, 8px)',
          marginLeft: '5px',
          fontWeight: 'bold',
          fontSize: '14px'
        }}>{field.label}</FormLabel>
        <TextField
          variant="outlined"
          margin="normal"
          {...field.props}
          sx={{ ...commonStyles, ...fieldStyles }}
          size='small'
          onChange={(e) => handleChange(field.props.name, e.target.files[0])}
          FormHelperTextProps={{ style: { fontSize: '10px', marginLeft: '0px' } }}
        />
      </FormControl>
    </Box>
    );
    case 'select':
      return (
        <Box key={index} sx={{ ...fieldStyles, display: 'flex', justifyContent: 'center', width: '100%' }}>
          <FormControl key={index} variant="outlined" fullWidth margin="normal" sx={{ maxWidth: '95%' }}>
            <FormLabel style={{
              boxSizing: 'border-box',
              color: 'var(--color-text-form-label-ttmnb4, #16191f)',
              display: 'inline',
              fontSize: 'var(--font-size-body-m-pa3mqb, 14px)',
              lineHeight: 'var(--line-height-body-m-2zx78l, 22px)',
              fontWeight: 'var(--font-display-label-weight-2njs7q, 400)',
              marginInlineEnd: 'var(--space-xs-xf5ch3, 8px)',
              fontWeight: 'bold'
            }}>{field.label}</FormLabel>
            <Select
              {...field.props}
              onChange={(e) => handleChange(field.props.name, e.target.value)}
              sx={{ ...commonStyles, ...fieldStyles }}
            >
              {field.props.options.map((option, idx) => (
                <MenuItem key={idx} value={option.value}>
                  {option.label}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
      );
    case 'multiselect':
      return (
        <FormControl key={index} variant="outlined" fullWidth margin="normal" sx={{ maxWidth: '95%' }}>
          <FormLabel style={{
            boxSizing: 'border-box',
            color: 'var(--color-text-form-label-ttmnb4, #16191f)',
            display: 'inline',
            fontSize: 'var(--font-size-body-m-pa3mqb, 14px)',
            lineHeight: 'var(--line-height-body-m-2zx78l, 22px)',
            fontWeight: 'var(--font-display-label-weight-2njs7q, 400)',
            marginInlineEnd: 'var(--space-xs-xf5ch3, 8px)',
            fontWeight: 'bold'
          }}>{field.label}</FormLabel>
          <Select
            multiple
            {...field.props}
            value={field.props.value || []}
            onChange={(e) => handleChange(field.props.name, e.target.value)}
            sx={{ ...commonStyles, ...fieldStyles }}
          >
            {field.options.map((option, idx) => (
              <MenuItem key={idx} value={option.value}>
                {option.label}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      );
    case 'checkbox':
      return (
        <FormControlLabel
          key={index}
          control={<Checkbox {...field.props} sx={{ color: 'var(--awsui-color-blue-600)', '&.Mui-checked': { color: 'var(--awsui-color-blue-700)' } }} />}
          label={field.label}
          sx={{ ...commonStyles, ...fieldStyles }}
        />
      );
    case 'radio':
      return (
        <Box key={index} sx={{ ...fieldStyles, display: 'flex', justifyContent: 'center', width: '100%', flexDirection: 'row', flexWrap: 'nowrap' }}>
          <FormControl fullWidth margin="normal" component="fieldset" sx={{ maxWidth: '95.8%' }}>
            <FormLabel style={{
              boxSizing: 'border-box',
              color: 'var(--color-text-form-label-ttmnb4, #16191f)',
              display: 'inline',
              fontSize: 'var(--font-size-body-m-pa3mqb, 14px)',
              lineHeight: 'var(--line-height-body-m-2zx78l, 22px)',
              marginInlineEnd: 'var(--space-xs-xf5ch3, 8px)',
              marginLeft: '5px',
              fontWeight: 'bold',
              fontSize: '14px'
            }}>{field.label}</FormLabel>
            <RadioGroup
              {...field.props}
              style={{ display: 'flex', justifyContent: 'center', flexDirection: 'row', flexWrap: 'nowrap' }}
              onChange={(e) => handleChange(field.props.name, e.target.value)}
            >
              {field.options.map((option, idx) => (
                <FormControlLabel
                  key={idx}
                  value={option.value}
                  control={<Radio />}
                  style={{ width: '50%', height: '70px', border: '1px solid lightgrey', borderRadius: '5px', padding: '7px', margin: '5px', fontSize: '12px' }}
                  label={
                    <span style={{ display: 'flex', flexDirection: 'column', textWrap: 'wrap', width: '350px' }}>
                      <span style={{ fontSize: '13px', fontWeight: '400' }}>{option.label}</span>
                      {option.description && <Typography variant="body2" color="textSecondary" style={{ fontSize: '10px', color: "rgb(150, 150, 150)" }}>{option.description}</Typography>}
                    </span>
                  }
                />
              ))}
            </RadioGroup>
          </FormControl>
        </Box>
      );
    case 'ruleType':
      return (
        <Grid container spacing={2} key={index}>
          {field.options.map((option, idx) => (
            <Grid item xs={option.colSpan || 6} key={idx} style={{ gridRowEnd: `span ${option.rowSpan || 1}` }}>
              <FormControl component="fieldset" margin="normal" sx={{ maxWidth: '95%' }}>
                <FormLabel component="legend">{option.label}</FormLabel>
                <RadioGroup {...field.props}>
                  <FormControlLabel value={option.value} control={<Radio />} label={option.label} />
                </RadioGroup>
                <Typography variant="body2" color="textSecondary">
                  {option.description}
                </Typography>
              </FormControl>
            </Grid>
          ))}
        </Grid>
      );
    case 'group':
      return (
        <Grid container spacing={2} key={index} style={{ gridRowEnd: `span ${field.rowSpan || 1}` }}>
          {field.fields.map((nestedField, nestedIndex) => (
            <Grid item xs={nestedField.colSpan || 12} key={nestedIndex} style={{ gridRowEnd: `span ${nestedField.rowSpan || 1}` }}>
              {renderField(nestedField, nestedIndex, handleChange, formData)}
            </Grid>
          ))}
        </Grid>
      );
    case 'form':
      return (
        <Box key={index} sx={{ marginBottom: 4 }}>
          <Typography variant="h6" sx={{ marginBottom: 2 }}>
            {field.label}
          </Typography>
          {field.fields.map((nestedField, nestedIndex) => renderField(nestedField, nestedIndex, handleChange, formData))}
        </Box>
      );
    case 'conditional':
      const conditionMet = formData[field.condition.field] === field.condition.value;
      return conditionMet ? field.fields.map((nestedField, nestedIndex) => renderField(nestedField, nestedIndex, handleChange, formData)) : null;
    case 'todoList':
      return (
        <Box key={index} sx={{ ...fieldStyles, display: 'flex', justifyContent: 'center', width: '95%', flexDirection: 'column', flexWrap: 'nowrap', margin: 'auto' }}>
          <FormLabel style={{
            boxSizing: 'border-box',
            color: 'var(--color-text-form-label-ttmnb4, #16191f)',
            display: 'inline',
            fontSize: 'var(--font-size-body-m-pa3mqb, 14px)',
            lineHeight: 'var(--line-height-body-m-2zx78l, 22px)',
            marginInlineEnd: 'var(--space-xs-xf5ch3, 8px)',
            marginLeft: '5px',
            fontWeight: 'bold',
            fontSize: '14px'
          }}>{field.label}</FormLabel>
          {formData[field.props.name] && formData[field.props.name].map((item, idx) => (
            <Box key={idx} sx={{ display: 'flex', gap: 2, marginBottom: 2 }}>
              <TextField
                variant="outlined"
                margin="normal"
                placeholder="Key"
                value={item.key}
                onChange={(e) => {
                  const newItems = [...formData[field.props.name]];
                  newItems[idx].key = e.target.value;
                  handleChange(field.props.name, newItems);
                }}
                sx={{ ...commonStyles, ...fieldStyles }}
                size='small'
              />
              <TextField
                variant="outlined"
                margin="normal"
                placeholder="Value"
                value={item.value}
                onChange={(e) => {
                  const newItems = [...formData[field.props.name]];
                  newItems[idx].value = e.target.value;
                  handleChange(field.props.name, newItems);
                }}
                sx={{ ...commonStyles, ...fieldStyles }}
                size='small'
              />
              <Button
                size="small"
                variant="text"
                color="error"
                onClick={() => {
                  const newItems = formData[field.props.name].filter((_, i) => i !== idx);
                  handleChange(field.props.name, newItems);
                }}
                sx={{ maxWidth: '30px', padding: '5px' }}
              >
                <Remove fontSize="small" />
              </Button>
            </Box>
          ))}
          <Button
            size="small"
            variant="outlined"
            color="info"
            onClick={() => {
              const newItems = [...(formData[field.props.name] || []), { key: '', value: '' }];
              handleChange(field.props.name, newItems);

            }}
            sx={{ width: '30px', height: '30px !important', padding: '1px', fontWeight: "800" }}

          >
            <Add fontSize="small" />
          </Button>
        </Box>
      );
    default:
      return null;
  }
};

const renderFields = (fields, handleChange, formData) => {
  return (
    <Grid container spacing={0}>
      {fields.map((field, index) => (
        <Grid spacing={0} item xs={field.colSpan || 12} key={index} style={{ gridRowEnd: `span ${field.rowSpan || 1}` }}>
          {renderField(field, index, handleChange, formData)}
        </Grid>
      ))}
    </Grid>
  );
};

const Form = ({ formConfig, handleFormData }) => {
  const [formData, setFormData] = useState(initializeFormData(formConfig.fields));

  const handleChange = (name, value) => {
    console.log(name, value)
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
    handleFormData(formData)
  };

  return (
    <Box className="form" height={"400px"} >
      <Typography variant="h6" sx={{ width: '95%', marginBottom: 2, color: 'var(--awsui-color-grey-600)', margin: 'auto', justifyContent: 'center' }}>
        {formConfig.formHeading}
      </Typography>
      <Box sx={{ display: formConfig.layout === 'single-line' ? 'flex' : 'block', flexWrap: 'wrap', gap: 2 }}>
        {renderFields(formConfig.fields, handleChange, formData)}
      </Box>
    </Box>
  );
};

Form.propTypes = {
  formConfig: PropTypes.shape({
    formHeading: PropTypes.string,
    layout: PropTypes.oneOf(['single-line', 'separate-line']),
    fields: PropTypes.arrayOf(
      PropTypes.shape({
        type: PropTypes.string.isRequired,
        label: PropTypes.string,
        props: PropTypes.object,
        options: PropTypes.arrayOf(
          PropTypes.shape({
            value: PropTypes.any.isRequired,
            label: PropTypes.string.isRequired,
            description: PropTypes.string,
            colSpan: PropTypes.number,
            rowSpan: PropTypes.number
          })
        ),
        onClick: PropTypes.func,
        variant: PropTypes.string,
        color: PropTypes.string,
        colSpan: PropTypes.number,
        rowSpan: PropTypes.number,
        fields: PropTypes.array, // For nested fields
        condition: PropTypes.shape({
          field: PropTypes.string.isRequired,
          value: PropTypes.any.isRequired
        }),
        styles: PropTypes.object // For custom styles
      })
    ).isRequired,
    backgroundColor: PropTypes.string,
    borderRadius: PropTypes.string,
    border: PropTypes.string,
    minHeight: PropTypes.string,
    width: PropTypes.string,
    position: PropTypes.string
  }).isRequired
};

export default Form;
