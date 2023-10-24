package fir

import chisel3._
import chisel3.util._

class FirFilter(bitWidth: Int, taps: Seq[SInt]) extends Module {
  val io = IO(new Bundle {
    val in = Input(SInt(bitWidth.W))
    val out = Output(SInt())
  })

  val buffer = Reg(Vec(taps.length, SInt(bitWidth.W)))
  buffer(0) := io.in
  for (i <- 1 until taps.length) {
    buffer(i) := buffer(i-1)
  }

  val muls = VecInit.tabulate(taps.length)(i => taps(i) * buffer(i))

  io.out := muls.reduce(_ +& _)
}