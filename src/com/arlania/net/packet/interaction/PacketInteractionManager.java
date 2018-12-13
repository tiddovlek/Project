package com.arlania.net.packet.interaction;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.arlania.model.Item;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles packet interaction
 * 
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class PacketInteractionManager {

	/**
	 * Represents all the packet interactions
	 */
	private static final ArrayList<PacketInteraction> interactions = new ArrayList<PacketInteraction>();

	/**
	 * Loads all the packet interaction
	 */
	public static void init() {
		load("com.ruthlessps.world.content.gambling");
		load("com.ruthlessps.world.content.minigame.impl");

	}

	/**
	 * Checks the button interaction
	 * 
	 * @param player
	 *            the player
	 * @param button
	 *            the button
	 * @return interaction
	 */
	public static boolean checkButtonInteraction(Player player, int button) {
		for (PacketInteraction action : interactions) {
			if (action == null) {
				continue;
			}
			if (action.handleButtonInteraction(player, button)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks the item container interaction
	 * 
	 * @param player
	 *            the player
	 * @param interfaceId
	 *            the interface id
	 * @param slot
	 *            the slot
	 * @param item
	 *            the item
	 * @param type
	 *            the type
	 * @return interaction
	 */
	public static boolean checkItemContainerInteraction(Player player, int interfaceId, int slot, Item item, int type) {
		for (PacketInteraction action : interactions) {
			if (action == null) {
				continue;
			}

			if (action.handleItemContainerInteraction(player, interfaceId, slot, item, type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Loads a directory with packet interaction
	 * 
	 * @param directory
	 *            the directory
	 */
	@SuppressWarnings("rawtypes")
	private static void load(String directory) {
		try {
			/**
			 * The classes
			 */
			Class[] classes = getClasses(directory);
			/**
			 * Loops through classes
			 */
			for (Class c : classes) {
				/**
				 * Invalid class
				 */
				if (c.isAnonymousClass() || c.isEnum() || c.isLocalClass()) {
					continue;
				}
				/**
				 * The instance
				 */
				Object o = c.newInstance();
				if (o == null) {
					continue;
				}
				/**
				 * Not game interface
				 */
				if (!(o instanceof PacketInteraction)) {
					continue;
				}
				/**
				 * Creates interface
				 */
				interactions.add((PacketInteraction) o);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile().replaceAll("%20", " ")));
		}
		ArrayList<Class> classes = new ArrayList<Class>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	@SuppressWarnings("rawtypes")
	private static List<Class> findClasses(File directory, String packageName) {
		List<Class> classes = new ArrayList<Class>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
			} else if (file.getName().endsWith(".class")) {
				try {
					classes.add(Class
							.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				} catch (Throwable e) {

				}
			}
		}
		return classes;
	}

	/**
	 * Gets the packet interactions
	 * 
	 * @return the interactions
	 */
	public static ArrayList<PacketInteraction> getInteractions() {
		return interactions;
	}
}
