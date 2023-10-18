package fir

import Chisel.SInt

import chisel3._
import chisel3.util._
import chisel3.stage.{ChiselStage, ChiselGeneratorAnnotation}

object MainApp extends App {
  val coefficients = Utils.readCoefficients(8)
  val fir_verilog = Utils.emitVerilog(new FirFilter(8, coefficients))
  println(fir_verilog)
}
