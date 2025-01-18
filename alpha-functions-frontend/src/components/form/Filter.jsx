import React from 'react';
// ...existing code...

const Filter = ({ field }) => {
  switch (field.type) {
    case 'radio':
      return (
        <div className={`col-span-${field.colSpan} row-span-${field.rowSpan}`}>
          <label>{field.label}</label>
          {field.options.map(option => (
            <div key={option.value} className={`col-span-${option.colSpan} row-span-${option.rowSpan}`}>
              <input
                type="radio"
                name={field.props.name}
                value={option.value}
                defaultChecked={field.props.defaultValue === option.value}
              />
              <label>{option.label}</label>
              {option.description && <p>{option.description}</p>}
            </div>
          ))}
        </div>
      );
    // ...existing code...
    default:
      return null;
  }
};

export default Filter;
