package com.tiamaes.bike.connector.protocol.message;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.tiamaes.bike.common.bean.command.UnlockCommand;
import com.tiamaes.bike.common.bean.command.UnlockCommand.Control;

/**
 * 借还车消息
 * @author fei
 *
 */
public class Message8012 extends Message {

	private Body body;

	public Message8012(UnlockCommand command) {
		Header _header = new Header();
		_header.setId(0x8012);
		_header.setChildPackage(false);
		_header.setEnableRSA(false);
		_header.setTerminalId(command.getTerminalId());
		_header.setTerminalType(Header.Type.STATION);
		//TODO 下发的，终端编号没有地方获取
		_header.setTerminalNo(command.getTerminalId());
		Body _body=new Body();
		_body.setControl(command.getControl());
		_body.setVehicleId(command.getVehicleId());
		this.setHeader(_header);
		this.setBody(_body);
		this.setBytes(_body.getBytes());
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public class Body {
		private Control control;//控制标志
		private String vehicleId;//车辆编号

		public Control getControl() {
			return control;
		}



		public void setControl(Control control) {
			this.control = control;
		}




		public String getVehicleId() {
			return vehicleId;
		}

		public void setVehicleId(String vehicleId) {
			this.vehicleId = vehicleId;
		}



		public byte[] getBytes() {
			StringBuffer bufferStr = new StringBuffer();
			bufferStr.append(String.format("%02X", control.ordinal()));
			bufferStr.append(String.format("%08X", Integer.valueOf(vehicleId)));
			try {
				return Hex.decodeHex(bufferStr.toString().toCharArray());
			} catch (DecoderException e) {
				return null;
			}
		}
	}


}
