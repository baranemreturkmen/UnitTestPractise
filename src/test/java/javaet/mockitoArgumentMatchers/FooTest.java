package javaet.mockitoArgumentMatchers;

import javaet.unitTestPractiseImp.Foo;

//import org.junit.Test;
/*No tests found for given includes: ... Hatası veriyor eger yukariyi import edersen.*/
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
/*Dogru olan bu.*/
import java.util.Arrays;
import java.util.List;

import static org.mockito.AdditionalMatchers.aryEq;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

class FooTest {

    @Test
    void bool() {

        /*Mock objesi olusturdum.*/
        Foo mockFoo = mock(Foo.class);

        /*We usually mock the behavior using when() and thenReturn() on the mock object.
         * Mockito Argument Matchers – any()
          Sometimes we want to mock the behavior for any argument of the given type, in that case,
         * we can use Mockito argument matchers. Mockito argument methods are defined in
         * org.mockito.ArgumentMatchers class as static methods.
         * Mock the behaviour -> metodu mocklamak, davranisi mocklamak.*/
        when(mockFoo.bool(anyString(), anyInt(), any(Object.class))).thenReturn(true);

        /*eq()
        When we use argument matchers, then all the arguments should use matchers. If we want to use a
        specific value for an argument, then we can use eq() method
        Sen argument matcher kullaniyorsan parametrelerinde tum parametreler argument matcher olmali.
        when arkada muhtemelen yazilirken arkada bu esneklikle yazilmamis(Override edilirken vs. vs.).
        Bu durumda spesifik bir deger vermek istedigimde eq argument matcher'i kullaniyorum.
        */
        when(mockFoo.bool(eq("false"), anyInt(), any(Object.class))).thenReturn(false);

        /*We are stubbing bool() method to return true for any string, integer and object arguments.
          All the below assertions will pass in this case.
          metod true donsun. Sonucta boolean donuyor biz false donecek sekilde yazmis olsak bile
          boyle bir ihtimal metodun donus tipiden dolayi her zaman olacak.
        */
        Assertions.assertTrue(mockFoo.bool("A", 1, "A"));
        Assertions.assertTrue(mockFoo.bool("B", 10, new Object()));

        /*Metod false donsun.*/
        Assertions.assertFalse(mockFoo.bool("false", 10, new Object()));

        /*assertFalse, assertTrue sadece boolean metodlarin testi yapilirken kullaniliyor.
        * assert'in kelime anlami one surmek demek. Yani sen bu testin sonunda assertTrue
        * diyerek testten true donmesini sagliyorsun ama metodun kendisi boolean donmuyorsa
        * eger testin kendisi olmasi gerektigi gibi bunu kabul etmeyecektir ve hata verecektir.*/
    }

    @Test
    void in(){

        Foo mockFoo = mock(Foo.class);

        /*Arrays.asList() creates an immutable list from an array. Hence it
          can be used to instantiate a list with an array.*/
        List<String> strs = Arrays.asList("A","B");

        /*There are argument matchers for the list, set, and map too.
        * Bu bayagi faydali aslinda. Test edecegim metod icin listenin kendisini
        * vs. olusturmmam gerekmeyen caselerde bayagi kullanisli.*/
        when(mockFoo.in(anyBoolean(), anyList())).thenReturn(10);

        /*If you want to match with arrays, then use any() method like this:
          any(byte[].class)
          any(Object[].class)
        */

        /*Assertion yaparken when'den donen degeri mutlaka kullaniyoruz.
        * Buraya 10 yerine 100 girsen mesela test fail olacaktir.
        * assertEquals aslinda when'den donen deger ile metoda parametre verdigimiz
        * zaman donecek degerler esit mi buna bakiyor.*/
        Assertions.assertEquals(10,mockFoo.in(true,strs));

        /*Mockito argument matchers can be used only with when() and verify()
          methods. -> Bu cok onemli bir bilgi.
          Let’s look at a few examples of using argument matchers
          in Mockito verify method.*/

        verify(mockFoo, atLeast(0)).bool(anyString(), anyInt(), any(Object.class));
        verify(mockFoo, atLeast(0)).bool(eq("false"), anyInt(), any(Object.class));

    }

    @Test
    void bar(){
        Foo mockFoo = mock(Foo.class);

        /*Mockito AdditionalMatchers
          Mockito org.mockito.AdditionalMatchers class provides some rarely used matchers.
          We can specify arguments to be greater than, less than, perform OR, AND, NOT operations.
          We can also check for equality of arrays. So if we call bar() method with any byte array
          as argument, second argument as { "A", "B" } and third argument greater than 10, then
          the stubbed method will return 11.*/

        /*bar metodu int deger dondugu icin thenReturn ile int bir deger dondurmek zorundayiz.*/
        when(mockFoo.bar(any(byte[].class), aryEq(new String[] { "A", "B" }), gt(10))).thenReturn(11);

        /*20'yi veya 99'u 10 yaptiginda ortalik karisiyor. cunku when ile birlikte 10 buyuk degerin donmesi
        * gerektigini soyluyorsun. İlk verilen 11 degerini 10'dan buyuk farkli bir deger yaparsan da test fail
        * oluyor. Cunku when ile tam olarak 11 donmesi gerektigini soyluyorsun.*/
        Assertions.assertEquals(11, mockFoo.bar("abc".getBytes(), new String[] { "A", "B" }, 20));
        Assertions.assertEquals(11, mockFoo.bar("xyz".getBytes(), new String[] { "A", "B" }, 99));
    }

    /*Mockito argument matcher methods are very useful in stubbing behaviors in a generic way.
      There are many methods to cover almost all the requirements*/
}
