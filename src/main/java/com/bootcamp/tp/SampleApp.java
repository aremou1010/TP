/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.tp;

//import com.bootcamp.tp.service.v1.LivrableRestService;
import com.bootcamp.tp.rest.v1.LivrableRestService;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author leger
 */
@ApplicationPath("rest")
public class SampleApp extends Application{
    /**
* configuration pour swagger
* du fait de l'utilisation d'une sous classe Application
* differente de la solution qui configure le web xml
*/

//@Override
public Set<Class<?>> getClasses() {

    Set<Class<?>> resources=new HashSet<>();
    resources.add(LivrableRestService.class);

    return resources;
}


}
