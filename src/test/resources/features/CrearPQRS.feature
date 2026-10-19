Feature: Gestión de PQR

  @PQRS @Destructive @RequiresQAData
  Scenario Outline: Crear una PQR exitosamente
    Given el usuario carga la informacion PQRS desde el Excel "<fila>"
      | rutaExcel                  | hoja       |
      | /excel/DatosPruebaWeb.xlsx | Datos PQRS |
    And el usuario PQRS abre la pagina de bonBonite
    And el usuario PQRS inicia sesion
    When ingresa a la opcion crear solicitud PQRS
    And diligencia el formulario con los datos requeridos
    Then el sistema genera un mensaje de confirmacion con el numero de radicado
    Examples:
      | fila |
      | 1    |