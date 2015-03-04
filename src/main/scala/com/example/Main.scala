package com.example

import org.apache.camel.scala.dsl.builder.RouteBuilder
import org.apache.camel.CamelContext
import org.apache.camel.Exchange
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.scala.dsl.builder.RouteBuilderSupport

object Main extends App {

  override def main(args: Array[String]): Unit = {
    execute
  }

  def execute {
    println("Start Process")
    val context: CamelContext = new DefaultCamelContext
    
    val routeBuilder = new RouteBuilder {
      from("file:/Users/rafaelsalerno/Documents/tmp/input").process ( exchange => ReadFile.read(exchange) )
    }

    context.addRoutes(routeBuilder)
    context.start

    while(true){}
    println("End process")
  }
  
}

object ReadFile{
  
  def read(exchange:Exchange ){
    val file = exchange.getIn().getBody().toString()
     println("Read File: "+file)
     SendFile.sendFile(getFileName(file),clenPath(file))
  }
  
  private def getFileName(path:String) = clenPath(path).split("/").last
  
  private def clenPath(path:String) =  path.replace("]","").replace("[","").replace("GenericFile","")
  
}

