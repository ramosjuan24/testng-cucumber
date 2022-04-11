Feature: Buscar Productos Tottus

  Quiero buscar productos en la web de tottus

  @TestngScenario
  Scenario: Buscar productos tottus y agregar a carrito de compras
    Given ingreso al buscador de google
    When escribo la palabra "sucursales tottus"
    Then Realizo clic en "sucursales tottus" de Google
    Then Deveria ver que el primer resultado de Google es "Tottus"
    And Selecciono la tercera opcion "https://www.tottus.cl"
    Then realizo clic en todas las categorias
    Then selecciono Productos Tottus
    And realizo la busqueda
    And ordeno por popularidad
    Then debo poder visualizar el detalle


