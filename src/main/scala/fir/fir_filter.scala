package fir

import chisel3._
import chisel3.util._

class FirFilter(bitWidth: Int, coeffs: Seq[SInt]) extends Module {
  val io = IO(new Bundle {
    val in = Input(SInt(bitWidth.W))
    val out = Output(SInt())
  })

  val zs = Reg(Vec(coeffs.length, SInt(bitWidth.W)))
  zs(0) := io.in
  for (i <- 1 until coeffs.length) {
    zs(i) := zs(i-1)
  }

  val products = VecInit.tabulate(coeffs.length)(i => zs(i) * coeffs(i))

  io.out := products.reduce(_ +& _)
}