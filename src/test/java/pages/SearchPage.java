package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import runner.CucumberRunner;

public class SearchPage extends CucumberRunner {


    @Step("Clic en Todas las Categorias")
    public void doSeleccionTodasLasCategorias() throws Exception{

        waitForElement(By.xpath(" //*[@id='Header']/div[2]/div[1]/div[1]/section/div[1]/div/div/button/span")).click();
        sacarPantallazo("Todas Las Categorias");
    }

    @Step("Clic en Productos tottus")
    public void doSeleccionProductosTottus() throws Exception{

        waitForElement(By.cssSelector("li:nth-child(11) > span")).click();
        sacarPantallazo("Productos Tottus");

    }

    @Step("Buscar la palabra Jugo")
    public void doBuscarJugo() throws Exception{

        //JavascriptExecutor js =  (JavascriptExecutor) driver;
        //js.executeScript("document.querySelector('#catalystSearchBar').value='Jugo'");

        waitForElement(By.xpath("//html//div[@id='Header']/div[2]//section//input[@id='catalystSearchBar']")).click();
        waitForElement(By.xpath("//html//div[@id='Header']/div[2]//section//input[@id='catalystSearchBar']")).sendKeys("Jugo");

        sacarPantallazo("Buscando la palabra Jugo");
        waitForElement(By.xpath("//html//div[@id='Header']/div[2]//section//input[@id='catalystSearchBar']")).sendKeys(Keys.ENTER);

    }


    @Step("Ordenar por Popularidad")
    public void doOrdenar() throws Exception{

        waitForElement(By.xpath("//*[@id='container']/div[1]/section/div[2]/div[2]/div[1]/div/div/div/div/button/div")).click();
        waitForElement(By.xpath("//li[contains(text(),'Popularidad')]")).click();
        sacarPantallazo("Ordenando por Popularidad");


    }


    @Step("Seleccion de Producto")
    public void doSeleccionandoProducto() throws Exception{

        waitForElement(By.cssSelector("li.product")).click();
        sacarPantallazo("Seleccion de primer producto");
    }


    @Step("Agregar a Carrito")
    public void doAgregarCarro() throws Exception{

        waitForElement(By.cssSelector("li.product")).click();
        sacarPantallazo("Seleccion de primer producto");


    }

}
