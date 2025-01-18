export const formConfigs = [
  {
    "fields": [
      {
        "type": "heading",
        "label": "Function detail",
        "colSpan": 12,
        "rowSpan": 1
      },
      {
        "type": "text",
        "label": "name",
        "props": {
          "name": "alphaFunctionName",
          "placeholder": "function-name",
          "inputProps": {
            "maxLength": 64,
            "pattern": "^[a-zA-Z0-9._-]*$"
          },
          "helperText": "Maximum of 64 characters consisting of numbers, lower/upper case letters, .,-,_"
        },
        "colSpan": 12,
        "rowSpan": 1
      },
      {
        "type": "text",
        "label": "Description - optional",
        "props": {
          "name": "alphaFunctionDescription",
          "placeholder": "Enter description",
        },
        "colSpan": 12,
        "rowSpan": 1
      },
      {
        "type": "radio",
        "label": "Execution details",
        "props": {
          "name": "executionDetails",
          "defaultValue": "alphaFunctionRepositoryImage"
        },
        "options": [
          {
            "value": "alphaFunctionRepositoryImage",
            "label": "Repository image",
            "description": "A function that runs with a pre built repository image.",
            "colSpan": 6
          },
          {
            "value": "alphaFunctionFiles",
            "label": "Executable file",
            "description": "A function that runs with a custom executable file.",
            "colSpan": 6
          }
        ],
        "colSpan": 12,
        "rowSpan": 1
      }
    ]
  },
  {
    "fields": [
      {
        "type": "heading",
        "label": "Execution detail",
        "colSpan": 12,
        "rowSpan": 1
      },
      {
        "type": "conditional",
        "condition": {
          "field": "executionDetails",
          "value": "alphaFunctionFiles"
        },
        "fields": [
          {
            "type": "file",
            "label": "Upload Executable file",
            "props": {
              "name": "alphaFunctionFiles",
              "placeholder": "Enter event pattern",
              "type": "file"
            },
            "colSpan": 12,
            "rowSpan": 1,
            "styles": {
            }
          }
        ]
      },
      {
        "type": "conditional",
        "condition": {
          "field": "executionDetails",
          "value": "alphaFunctionRepositoryImage"
        },
        "fields": [
          {
            "type": "text",
            "label": "Repository username",
            "props": {
              "name": "repositoryUserName",
              "placeholder": "Enter repository username ",
              "inputProps": {
                "maxLength": 64,
                "pattern": "^[a-zA-Z0-9._-]*$"
              },
              "helperText": "Image name will be repository_username/alpha_function_name. You should have a remove repository image with this name."
            },
            "colSpan": 12,
            "rowSpan": 1
          }
        ]
      },
      {
        "type": "text",
        "label": "Execution handler - function",
        "props": {
          "name": "alphaFunctionHandler",
          "placeholder": "Enter handler function eg. com.example::handler",
        },
        "colSpan": 12,
        "rowSpan": 1
      },
      {
        "type": "text",
        "label": "Active Deadline - seconds",
        "props": {
          "name": "alphaFunctionTimeout",
          "placeholder": "Enter function timeout in seconds.",
        },
        "colSpan": 12,
        "rowSpan": 1
      }
    ]
  },
  {
    "fields": [
      {
        "type": "heading",
        "label": "Parallel Execution",
        "colSpan": 12,
        "rowSpan": 1
      },
      {
        "type": "radio",
        "label": "",
        "props": {
          "name": "parallelExecutionEnabled",
          "defaultValue": "false"
        },
        "options": [
          {
            "value": "false",
            "label": "Default",
            "description": "You can't define multiple replicas. Defaults to single replica.",
            "colSpan": 6
          },
          {
            "value": "true",
            "label": "Parallel Execution",
            "description": "You can define an one or multiple parallellely executing replicas.",
            "colSpan": 6
          }
        ],
        "colSpan": 12,
        "rowSpan": 1
      },
      {
        "type": "conditional",
        "condition": {
          "field": "parallelExecutionEnabled",
          "value": "false"
        },
        "fields": [
          {
            "type": "text",
            "label": "Parallel Replicas",
            "props": {
              "name": "parallelReplicas",
              "placeholder": "Enter number of parallel replicas",
              "inputProps": {
                "maxLength": 64,
                "pattern": "^[0-9]*$"
              },
              "disabled": true,
              "defaultValue": 1,
              "helperText": "Number of parallel replicas. Cannot be changed."
            },
            "colSpan": 12,
            "rowSpan": 1
          }
        ]
      }, {
        "type": "conditional",
        "condition": {
          "field": "parallelExecutionEnabled",
          "value": "true"
        },
        "fields": [
          {
            "type": "text",
            "label": "Parallel Replicas",
            "props": {
              "name": "parallelReplicas",
              "placeholder": "Enter number of parallel replicas",
              "inputProps": {
                "maxLength": 64,
                "pattern": "^[0-9]*$"
              },
              "helperText": "Number of parallel replicas. Must be greater than or equal to 1."
            },
            "colSpan": 12,
            "rowSpan": 1
          }
        ]
      }, {
        "type": "text",
        "label": "Max Retries",
        "props": {
          "name": "maxRetries",
          "placeholder": "Enter number of max retries per replica",
          "inputProps": {
            "maxLength": 64,
            "pattern": "^[0-9]*$"
          },
          "helperText": "Number of parallel replicas. Must be greater than or equal to 1."
        },
        "colSpan": 12,
        "rowSpan": 1
      }
    ]
  },
{
  "fields": [
    {
      "type": "heading",
      "label": "Configure Runtime",
      "colSpan": 12,
      "rowSpan": 1
    },
    {
      "type": "radio",
      "label": "Select Language",
      "props": {
        "name": "alphaFunctionLanguage",
        "defaultValue": "java"
      },
      "options": [
        {
          "value": "java",
          "label": "Java",
          "colSpan": 6,
          "rowSpan": 1,
          "description": "Java is a class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible."
        },
        {
          "value": "python",
          "label": "Python",
          "colSpan": 6,
          "rowSpan": 1,
          "description": "Python is an interpreted, high-level and general-purpose programming language"
        }
      ],
      "colSpan": 12,
      "rowSpan": 1
    },
    {
      "type": "conditional",
      "condition": {
        "field": "alphaFunctionLanguage",
        "value": "java"
      },
      "fields": [
        {
          "type": "select",
          "label": "Java Version",
          "props": {
            "name": "alphaFunctionRuntime",
            "options": [
              { "value": "20", "label": "20" },
              { "value": "21", "label": "21" },
              { "value": "22", "label": "22" },
              { "value": "23", "label": "23" }
            ],
            "placeholder": "Select Java Version"
          },
          "colSpan": 6,
          "rowSpan": 1
        }
      ]
    }, {
      "type": "text",
      "label": "Max cpu",
      "props": {
        "name": "maximumCpu",
        "placeholder": "Unit: millicore",
        "inputProps": {
          "maxLength": 64,
          "pattern": "^[0-9]*$"
        },
        "helperText": "Maximum CPU that can be allocated to the function."
      },
      "colSpan": 6,
      "rowSpan": 1,
      "styles": {    marginLeft: "5px", marginRight: "0px",width: "99%"}

    }, {
      "type": "text",
      "label": "Min cpu",
      "props": {
        "name": "minimumCpu",
        "placeholder": "Unit: millicore",
        "inputProps": {
          "maxLength": 64,
          "pattern": "^[0-9]*$"
        },
        "helperText": "Minimim CPU that can be allocated to the function."
      },
      "colSpan": 6,
      "rowSpan": 1,
      "styles": {    marginLeft: "-1px",marginRight: "5px",width: "99%"}
    }
    , {
      "type": "text",
      "label": "Max memory",
      "props": {
        "name": "maximumMemory",
        "placeholder": "Unit: MiB",
        "inputProps": {
          "maxLength": 64,
          "pattern": "^[0-9]*$",
        },
        "helperText": "Maximum memory that can be allocated to the function."
      },
      "colSpan": 6,
      "rowSpan": 1,
      "styles": {    marginLeft: "5px", marginRight: "0px",width: "99%"}

    }
    , {
      "type": "text",
      "label": "Min memory",
      "props": {
        "name": "minimumMemory",
        "placeholder": "Unit: MiB",
        "inputProps": {
          "maxLength": 64,
          "pattern": "^[0-9]*$"
        },
        "helperText": "Minimum memory that can be allocated to the function."
      },
      "colSpan": 6,
      "rowSpan": 1,
      "styles": {    marginLeft: "-1px",marginRight: "5px",width: "99%"}
    }
  ]
},
{
  "fields": [
    {
      "type": "heading",
      "label": "Environment Variables",
      "colSpan": 12,
      "rowSpan": 1
    },
  {
    "type": "todoList",
    "label": "",
    "props": {
      "name": "envVariables",
      "placeholder": "Add environment variable",
      "fields": [
        {
          "type": "text",
          "label": "Key",
          "props": {
            "name": "key",
            "placeholder": "Enter key",
            "inputProps": {
              "maxLength": 64,
              "pattern": "^[a-zA-Z0-9._-]*$"
            }
          },
          "colSpan": 4,
          "rowSpan": 1,
        },
        {
          "type": "text",
          "label": "Value",
          "props": {
            "name": "value",
            "placeholder": "Enter value",
            "inputProps": {
              "maxLength": 256
            }
          },
          "colSpan": 4,
          "rowSpan": 1,
        }
      ]
    },
    "colSpan": 12,
    "rowSpan": 1
    }
  ]
}
];