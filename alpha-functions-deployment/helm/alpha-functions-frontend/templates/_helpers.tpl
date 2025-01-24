{{- define "alpha-functions-frontend.name" -}}
  {{ .Chart.Name }}
{{- end -}}

{{- define "alpha-functions-frontend.fullname" -}}
  {{ .Release.Name }}-{{ .Chart.Name }}
{{- end -}}
