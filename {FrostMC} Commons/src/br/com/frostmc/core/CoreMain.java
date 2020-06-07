package br.com.frostmc.core;

import br.com.frostmc.common.util.Util;
import br.com.frostmc.core.data.mysql.MySQL;
import br.com.frostmc.core.util.logger.MessageBox;

public class CoreMain {

	public MySQL mysql;
	public MessageBox box;
	public Util util;
	
	public CoreMain() {
		mysql = new MySQL();
		box = new MessageBox();
		util = new Util();
	}
	
	public MySQL getMySQL() { return mysql; }
	public MessageBox getBox() { return box; }
	public Util getUtil() { return util; }
	
}