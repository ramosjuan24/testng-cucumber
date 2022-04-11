package stepdefinition;

import io.cucumber.java.eo.Se;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import pages.SearchPage;
import runner.CucumberRunner;

/**
 * @autor   Ing. Juan Carlos Ramos
 * @version v1.0
 * @serialData 000000000134
 */

public class Test001 extends CucumberRunner {

    SearchPage busqueda = new SearchPage();

    @Dado("ingreso al buscador de google")
    public void ingreso_al_buscador_de_google() {

        driver.get(baseUrl);
    }

    @Cuando("escribo la palabra {string}")
    public void in_la_palabra(String string) throws Exception {

        waitForElement(By.name("q")).sendKeys(string);
        sacarPantallazo("Page Home Google");

    }

    @Entonces("Realizo clic en {string} de Google")
    public void realizo_clic_en_de_Google(String string) throws Exception {

        waitForElement(By.name("q")).sendKeys(Keys.ENTER);

    }

    @Entonces("Deveria ver que el primer resultado de Google es {string}")
    public void deveria_ver_que_el_primer_resultado_de_Google_es(String string) throws Exception {

        //Supermercado Tottus - Compra confiable y segura
       boolean see =  waitForElement(By.partialLinkText("https://www.tottus.cl")).isDisplayed();
       Assert.assertTrue(see , "No se encontro el link cargado");
    }

    @Entonces("Selecciono la tercera opcion {string}")
    public void selecciono_la_tercera_opcion(String string) throws Exception {

       waitForElement(By.partialLinkText(string)).click();
       sacarPantallazo("Page Home Tottus");
    }

    @Entonces("realizo clic en todas las categorias")
    public void realizo_clic_en_todas_las_categorias() throws Exception {

        busqueda.doSeleccionTodasLasCategorias();
    }

    @Entonces("selecciono Productos Tottus")
    public void selecciono_Productos_Tottus() throws Exception {

        busqueda.doSeleccionProductosTottus();
    }

    @Y("realizo la busqueda")
    public void realizo_la_busqueda() throws Exception {

        busqueda.doBuscarJugo();
    }

    @Entonces("ordeno por popularidad")
    public void ordeno_por_popularidad() throws Exception {

        busqueda.doOrdenar();
    }

    @Entonces("debo poder visualizar el detalle")
    public void debo_poder_visualizar_el_detalle() throws Exception {

        busqueda.doSeleccionandoProducto();
    }



    @AfterMethod
    public void close(){
        driver.quit();
    }

}
