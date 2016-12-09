package wdl4s.values

import wdl4s.WdlExpressionException
import wdl4s.types.WdlFloatType

import scala.util.{Failure, Success, Try}

case class WdlFloat(value: Double) extends WdlPrimitive {
  val wdlType = WdlFloatType
  override def add(rhs: WdlValue): Try[WdlValue] = {
    rhs match {
      case r:WdlFloat => Success(WdlFloat(value + r.value))
      case r:WdlInteger => Success(WdlFloat(value + r.value))
      case r:WdlString => Success(WdlString(value + r.value))
      case WdlOptionalValue(_, Some(r)) => add(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this + $rhs")
    }
  }
  override def subtract(rhs: WdlValue): Try[WdlValue] = {
    rhs match {
      case r:WdlFloat => Success(WdlFloat(value - r.value))
      case r:WdlInteger => Success(WdlFloat(value - r.value))
      case WdlOptionalValue(_, Some(r)) => subtract(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this - $rhs")
    }
  }
  override def multiply(rhs: WdlValue): Try[WdlValue] = {
    rhs match {
      case r:WdlFloat => Success(WdlFloat(value * r.value))
      case r:WdlInteger => Success(WdlFloat(value * r.value))
      case WdlOptionalValue(_, Some(r)) => multiply(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this * $rhs")
    }
  }
  override def divide(rhs: WdlValue): Try[WdlValue] = {
    rhs match {
      case r:WdlFloat if r.value == 0.0 => Failure(new WdlExpressionException("Divide by zero"))
      case r:WdlFloat => Success(WdlFloat(value / r.value))
      case r:WdlInteger if r.value == 0 => Failure(new WdlExpressionException("Divide by zero"))
      case r:WdlInteger => Success(WdlFloat(value / r.value))
      case WdlOptionalValue(_, Some(r)) => divide(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this / $rhs")
    }
  }
  override def mod(rhs: WdlValue): Try[WdlValue] = {
    rhs match {
      case r:WdlFloat if r.value == 0.0 => Failure(new WdlExpressionException("Divide by zero"))
      case r:WdlFloat => Success(WdlFloat(value % r.value))
      case r:WdlInteger if r.value == 0 => Failure(new WdlExpressionException("Divide by zero"))
      case r:WdlInteger => Success(WdlFloat(value % r.value))
      case WdlOptionalValue(_, Some(r)) => mod(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this % $rhs")
    }
  }
  override def equals(rhs: WdlValue): Try[WdlBoolean] = {
    rhs match {
      case r:WdlFloat => Success(WdlBoolean(value == r.value))
      case r:WdlInteger => Success(WdlBoolean(value == r.value))
      case WdlOptionalValue(_, Some(r)) => equals(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this == $rhs")
    }
  }
  override def lessThan(rhs: WdlValue): Try[WdlBoolean] = {
    rhs match {
      case r:WdlFloat => Success(WdlBoolean(value < r.value))
      case r:WdlInteger => Success(WdlBoolean(value < r.value))
      case WdlOptionalValue(_, Some(r)) => lessThan(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this < $rhs")
    }
  }
  override def greaterThan(rhs: WdlValue): Try[WdlBoolean] = {
    rhs match {
      case r:WdlFloat => Success(WdlBoolean(value > r.value))
      case r:WdlInteger => Success(WdlBoolean(value > r.value))
      case WdlOptionalValue(_, Some(r)) => greaterThan(r)
      case r: WdlOptionalValue => emptyValue(r)
      case _ => invalid(s"$this > $rhs")
    }
  }
  override def unaryPlus: Try[WdlValue] = Success(WdlFloat(math.abs(value)))
  override def unaryMinus: Try[WdlValue] = Success(WdlFloat(-value))
  override def toWdlString = value.toString
}
