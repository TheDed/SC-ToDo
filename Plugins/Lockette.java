/*      */ package org.yi.acru.bukkit.Lockette;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.PrintStream;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.SocketTimeoutException;
/*      */ import java.net.URL;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.UUID;
/*      */ import java.util.logging.Logger;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.ChatColor;
/*      */ import org.bukkit.Effect;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.block.BlockFace;
/*      */ import org.bukkit.block.Sign;
/*      */ import org.bukkit.command.Command;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.configuration.InvalidConfigurationException;
/*      */ import org.bukkit.configuration.file.FileConfiguration;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.metadata.MetadataValue;
/*      */ import org.bukkit.plugin.PluginDescriptionFile;
/*      */ import org.json.simple.JSONArray;
/*      */ import org.json.simple.JSONObject;
/*      */ import org.json.simple.parser.JSONParser;
/*      */ import org.yi.acru.bukkit.BlockUtil;
/*      */ import org.yi.acru.bukkit.PluginCore;
/*      */ 
/*      */ public class Lockette extends PluginCore
/*      */ {
/*   39 */   static boolean DEBUG = false;
/*      */   
/*      */   private static Lockette plugin;
/*   42 */   private static boolean enabled = false;
/*      */   
/*   44 */   private static boolean uuidSupport = false;
/*   45 */   private static boolean registered = false;
/*   46 */   private final LocketteBlockListener blockListener = new LocketteBlockListener(this);
/*   47 */   private final LocketteEntityListener entityListener = new LocketteEntityListener(this);
/*   48 */   private final LockettePlayerListener playerListener = new LockettePlayerListener(this);
/*   49 */   private final LockettePrefixListener prefixListener = new LockettePrefixListener(this);
/*   50 */   private final LocketteWorldListener worldListener = new LocketteWorldListener(this);
/*   51 */   private final LocketteInventoryListener inventoryListener = new LocketteInventoryListener(this);
/*   52 */   protected final LocketteDoorCloser doorCloser = new LocketteDoorCloser(this);
/*      */   protected static boolean explosionProtectionAll;
/*      */   protected static boolean rotateChests;
/*      */   protected static boolean adminSnoop;
/*      */   protected static boolean adminBypass;
/*      */   protected static boolean adminBreak;
/*   58 */   protected static boolean protectDoors; protected static boolean protectTrapDoors; protected static boolean usePermissions; protected static boolean directPlacement; protected static boolean colorTags; protected static boolean debugMode; protected static boolean blockHopper = false;
/*      */   protected static int defaultDoorTimer;
/*      */   protected static String broadcastSnoopTarget;
/*      */   protected static String broadcastBreakTarget;
/*      */   protected static String broadcastReloadTarget;
/*      */   protected static boolean msgUser;
/*   64 */   protected static boolean msgOwner; protected static boolean msgAdmin; protected static boolean msgError; protected static boolean msgHelp; protected static String altPrivate; protected static String altMoreUsers; protected static String altEveryone; protected static String altOperators; protected static String altTimer; protected static String altFee; protected static List<Object> customBlockList = null; protected static List<Object> disabledPluginList = null;
/*      */   
/*   66 */   protected static FileConfiguration strings = null;
/*   67 */   protected final HashMap<String, Block> playerList = new HashMap();
/*      */   private static final String META_KEY = "LocketteUUIDs";
/*      */   
/*      */   public Lockette()
/*      */   {
/*   72 */     plugin = this;
/*      */   }
/*      */   
/*      */   public void onLoad() {}
/*      */   
/*      */   private static int majorVersion(String ver)
/*      */   {
/*      */     try
/*      */     {
/*   81 */       ver = ver.replaceAll("v", "");
/*   82 */       ver = ver.substring(0, ver.indexOf("_R"));
/*   83 */       String[] nums = ver.split("_");
/*   84 */       return Integer.parseInt(nums[0]);
/*      */     }
/*      */     catch (Exception localException) {}
/*   87 */     return -1;
/*      */   }
/*      */   
/*      */   private static int minorVersion(String ver) {
/*      */     try {
/*   92 */       ver = ver.replaceAll("v", "");
/*   93 */       ver = ver.substring(0, ver.indexOf("_R"));
/*   94 */       String[] nums = ver.split("_");
/*   95 */       return Integer.parseInt(nums[1]);
/*      */     }
/*      */     catch (Exception localException) {}
/*   98 */     return -1;
/*      */   }
/*      */   
/*      */   private static boolean leqVersion(String ver, int major, int minor) {
/*      */     try {
/*  103 */       int mj = majorVersion(ver);
/*  104 */       if (mj > major) {
/*  105 */         return true;
/*      */       }
/*  107 */       if (mj == major) {
/*  108 */         int mi = minorVersion(ver);
/*  109 */         if (mi >= minor) {
/*  110 */           System.out.println("minor >");
/*  111 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception localException) {}
/*  116 */     return false;
/*      */   }
/*      */   
/*      */   public void onEnable() {
/*  120 */     if (enabled) { return;
/*      */     }
/*  122 */     log.info("[" + getDescription().getName() + "] Version " + getDescription().getVersion() + " is being enabled!  Yay!  (Core version " + getCoreVersion() + ")");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  127 */     int recBuild = 2771;
/*  128 */     int minBuild = 2735;
/*      */     
/*  130 */     float build = getBuildVersion();
/*      */     int printBuild;
/*  132 */     int printBuild; if ((build > 399.0F) && (build < 400.0F)) printBuild = (int)((build - 399.0F) * 100.0F); else {
/*  133 */       printBuild = (int)build;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  159 */     String bukkitVersion = Bukkit.getServer().getClass().getName().split("\\.")[3];
/*  160 */     float bukkitver = minorVersion(bukkitVersion);
/*  161 */     float bukkitminver = 1.8F;
/*      */     
/*  163 */     if (!leqVersion(bukkitVersion, 1, 8)) {
/*  164 */       log.severe("[" + getDescription().getName() + "] Detected Bukkit build [" + bukkitVersion + "], but requires version [" + bukkitminver + "] or greater!");
/*  165 */       log.severe("[" + getDescription().getName() + "] Aborting enable!");
/*  166 */       return;
/*      */     }
/*  168 */     log.info("[" + getDescription().getName() + "] Detected Bukkit version [" + bukkitVersion + "] ok.");
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  173 */     loadProperties(false);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  178 */     super.onEnable();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  183 */     if (!registered) {
/*  184 */       this.blockListener.registerEvents();
/*  185 */       this.entityListener.registerEvents();
/*  186 */       this.playerListener.registerEvents();
/*  187 */       this.prefixListener.registerEvents();
/*  188 */       this.worldListener.registerEvents();
/*  189 */       this.inventoryListener.registerEvents();
/*  190 */       registered = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  196 */     log.info("[" + getDescription().getName() + "] Ready to protect your containers.");
/*  197 */     enabled = true;
/*      */   }
/*      */   
/*      */   public void onDisable()
/*      */   {
/*  202 */     if (!enabled) return;
/*  203 */     log.info(getDescription().getName() + " is being disabled...  ;.;");
/*      */     
/*  205 */     if ((protectDoors) || (protectTrapDoors)) {
/*  206 */       log.info("[" + getDescription().getName() + "] Closing all automatic doors.");
/*  207 */       this.doorCloser.cleanup();
/*      */     }
/*      */     
/*  210 */     super.onDisable();
/*      */     
/*  212 */     enabled = false;
/*      */   }
/*      */   
/*      */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
/*      */   {
/*  217 */     if (!cmd.getName().equalsIgnoreCase("lockette")) return false;
/*  218 */     if ((sender instanceof Player)) { return true;
/*      */     }
/*  220 */     if (args.length == 1) {
/*  221 */       if (args[0].equalsIgnoreCase("reload")) {
/*  222 */         loadProperties(true);
/*      */         
/*  224 */         localizedMessage(null, broadcastReloadTarget, "msg-admin-reload");
/*      */ 
/*      */ 
/*      */       }
/*  228 */       else if (args[0].equalsIgnoreCase("coredump")) {
/*  229 */         dumpCoreInfo();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  234 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */   protected void loadProperties(boolean reload)
/*      */   {
/*  240 */     if (reload) {
/*  241 */       log.info("[" + getDescription().getName() + "] Reloading plugin configuration files.");
/*  242 */       reloadConfig();
/*      */     }
/*      */     
/*      */ 
/*  246 */     FileConfiguration properties = getConfig();
/*  247 */     boolean propChanged = true;
/*      */     
/*      */ 
/*      */ 
/*  251 */     uuidSupport = properties.getBoolean("enable-uuid-support", false);
/*  252 */     properties.set("enable-uuid-support", Boolean.valueOf(uuidSupport));
/*  253 */     msgUser = properties.getBoolean("enable-messages-user", true);
/*  254 */     properties.set("enable-messages-user", Boolean.valueOf(msgUser));
/*  255 */     msgOwner = properties.getBoolean("enable-messages-owner", false);
/*  256 */     properties.set("enable-messages-owner", Boolean.valueOf(msgOwner));
/*      */     
/*  258 */     msgAdmin = properties.getBoolean("enable-messages-admin", true);
/*  259 */     properties.set("enable-messages-admin", Boolean.valueOf(msgAdmin));
/*  260 */     msgError = properties.getBoolean("enable-messages-error", true);
/*  261 */     properties.set("enable-messages-error", Boolean.valueOf(msgError));
/*  262 */     msgHelp = properties.getBoolean("enable-messages-help", true);
/*  263 */     properties.set("enable-messages-help", Boolean.valueOf(msgHelp));
/*      */     
/*  265 */     explosionProtectionAll = properties.getBoolean("explosion-protection-all", true);
/*  266 */     properties.set("explosion-protection-all", Boolean.valueOf(explosionProtectionAll));
/*  267 */     rotateChests = properties.getBoolean("enable-chest-rotation", false);
/*  268 */     properties.set("enable-chest-rotation", Boolean.valueOf(rotateChests));
/*      */     
/*  270 */     usePermissions = properties.getBoolean("enable-permissions", false);
/*  271 */     properties.set("enable-permissions", Boolean.valueOf(usePermissions));
/*  272 */     protectDoors = properties.getBoolean("enable-protection-doors", true);
/*  273 */     properties.set("enable-protection-doors", Boolean.valueOf(protectDoors));
/*  274 */     protectTrapDoors = properties.getBoolean("enable-protection-trapdoors", true);
/*  275 */     properties.set("enable-protection-trapdoors", Boolean.valueOf(protectTrapDoors));
/*      */     
/*      */ 
/*  278 */     adminSnoop = properties.getBoolean("allow-admin-snoop", false);
/*  279 */     properties.set("allow-admin-snoop", Boolean.valueOf(adminSnoop));
/*  280 */     adminBypass = properties.getBoolean("allow-admin-bypass", true);
/*  281 */     properties.set("allow-admin-bypass", Boolean.valueOf(adminBypass));
/*  282 */     adminBreak = properties.getBoolean("allow-admin-break", true);
/*  283 */     properties.set("allow-admin-break", Boolean.valueOf(adminBreak));
/*      */     
/*      */ 
/*  286 */     blockHopper = properties.getBoolean("enable-hopper-blocking", true);
/*  287 */     properties.set("enable-hopper-blocking", Boolean.valueOf(blockHopper));
/*      */     
/*      */ 
/*  290 */     if ((protectDoors) || (protectTrapDoors)) {
/*  291 */       if (this.doorCloser.start())
/*  292 */         log.severe("[" + getDescription().getName() + "] Failed to register door closing task!");
/*      */     } else {
/*  294 */       this.doorCloser.stop();
/*      */     }
/*      */     
/*  297 */     directPlacement = properties.getBoolean("enable-quick-protect", true);
/*  298 */     properties.set("enable-quick-protect", Boolean.valueOf(directPlacement));
/*  299 */     colorTags = properties.getBoolean("enable-color-tags", true);
/*  300 */     properties.set("enable-color-tags", Boolean.valueOf(colorTags));
/*      */     
/*      */ 
/*  303 */     debugMode = properties.getBoolean("enable-debug", false);
/*  304 */     if (debugMode) {
/*  305 */       log.warning("[" + getDescription().getName() + "] Debug mode is enabled, so Lockette chests are NOT secure.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  319 */     defaultDoorTimer = properties.getInt("default-door-timer", -1);
/*  320 */     if (defaultDoorTimer == -1) {
/*  321 */       defaultDoorTimer = 0;
/*  322 */       properties.set("default-door-timer", Integer.valueOf(defaultDoorTimer));
/*  323 */       propChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  329 */     customBlockList = properties.getList("custom-lockable-block-list");
/*  330 */     if (customBlockList == null) {
/*  331 */       customBlockList = new ArrayList(3);
/*  332 */       customBlockList.add(Integer.valueOf(Material.ENCHANTMENT_TABLE.getId()));
/*  333 */       customBlockList.add(Integer.valueOf(Material.JUKEBOX.getId()));
/*  334 */       customBlockList.add(Integer.valueOf(Material.DIAMOND_BLOCK.getId()));
/*  335 */       customBlockList.add(Integer.valueOf(Material.ANVIL.getId()));
/*  336 */       customBlockList.add(Integer.valueOf(Material.HOPPER.getId()));
/*  337 */       properties.set("custom-lockable-block-list", customBlockList);
/*  338 */       propChanged = true;
/*      */     }
/*  340 */     if (!customBlockList.isEmpty()) {
/*  341 */       log.info("[" + getDescription().getName() + "] Custom lockable block list: " + customBlockList.toString());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  347 */     disabledPluginList = properties.getList("linked-plugin-ignore-list");
/*  348 */     if (disabledPluginList == null) {
/*  349 */       disabledPluginList = new ArrayList(1);
/*  350 */       disabledPluginList.add("mcMMO");
/*  351 */       properties.set("linked-plugin-ignore-list", disabledPluginList);
/*  352 */       propChanged = true;
/*      */     }
/*  354 */     if (!disabledPluginList.isEmpty()) {
/*  355 */       log.info("[" + getDescription().getName() + "] Ignoring linked plugins: " + disabledPluginList.toString());
/*      */     }
/*      */     
/*      */ 
/*  359 */     broadcastSnoopTarget = properties.getString("broadcast-snoop-target");
/*  360 */     if (broadcastSnoopTarget == null) {
/*  361 */       broadcastSnoopTarget = "[Everyone]";
/*  362 */       properties.set("broadcast-snoop-target", broadcastSnoopTarget);
/*  363 */       propChanged = true;
/*      */     }
/*  365 */     broadcastBreakTarget = properties.getString("broadcast-break-target");
/*  366 */     if (broadcastBreakTarget == null) {
/*  367 */       broadcastBreakTarget = "[Everyone]";
/*  368 */       properties.set("broadcast-break-target", broadcastBreakTarget);
/*  369 */       propChanged = true;
/*      */     }
/*  371 */     broadcastReloadTarget = properties.getString("broadcast-reload-target");
/*  372 */     if (broadcastReloadTarget == null) {
/*  373 */       broadcastReloadTarget = "[Operators]";
/*  374 */       properties.set("broadcast-reload-target", broadcastReloadTarget);
/*  375 */       propChanged = true;
/*      */     }
/*      */     
/*      */ 
/*  379 */     String stringsFileName = properties.getString("strings-file-name");
/*  380 */     if ((stringsFileName == null) || (stringsFileName.isEmpty())) {
/*  381 */       stringsFileName = "strings-en.yml";
/*  382 */       properties.set("strings-file-name", stringsFileName);
/*  383 */       propChanged = true;
/*      */     }
/*      */     
/*  386 */     if (propChanged) {
/*  387 */       saveConfig();
/*      */     }
/*  389 */     loadStrings(reload, stringsFileName);
/*      */   }
/*      */   
/*      */   protected void loadStrings(boolean reload, String fileName)
/*      */   {
/*  394 */     boolean stringChanged = false;
/*      */     
/*  396 */     File stringsFile = new File(getDataFolder(), fileName);
/*      */     
/*      */ 
/*      */ 
/*  400 */     if (strings != null)
/*      */     {
/*  402 */       strings = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  407 */     strings = new org.bukkit.configuration.file.YamlConfiguration();
/*      */     try {
/*  409 */       strings.load(stringsFile);
/*      */     } catch (InvalidConfigurationException ex) {
/*  411 */       log.warning("[" + getDescription().getName() + "] Error loading " + fileName + ": " + ex.getMessage());
/*      */       
/*  413 */       if (!fileName.equals("strings-en.yml")) {
/*  414 */         loadStrings(reload, "strings-en.yml");
/*  415 */         return; }
/*  416 */       log.warning("[" + getDescription().getName() + "] Returning to default strings.");
/*      */     }
/*      */     catch (Exception localException) {}
/*      */     
/*      */ 
/*      */ 
/*  422 */     boolean original = false;
/*  423 */     if (fileName.equals("strings-en.yml")) {
/*  424 */       original = true;
/*      */       
/*  426 */       strings.set("language", "English");
/*      */       
/*      */ 
/*  429 */       if (original) {
/*      */         try {
/*  431 */           strings.save(stringsFile);
/*  432 */           strings.load(stringsFile);
/*      */         }
/*      */         catch (Exception localException1) {}
/*      */       }
/*      */       
/*  437 */       strings.set("author", "Acru");
/*  438 */       strings.set("editors", "");
/*  439 */       strings.set("version", Integer.valueOf(0));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  445 */     String tempString = strings.getString("language");
/*  446 */     if ((tempString == null) || (tempString.isEmpty())) {
/*  447 */       log.info("[" + getDescription().getName() + "] Loading strings file " + fileName);
/*      */     } else {
/*  449 */       log.info("[" + getDescription().getName() + "] Loading strings file for " + tempString + " by " + strings.getString("author"));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  454 */     altPrivate = strings.getString("alternate-private-tag");
/*  455 */     if ((altPrivate == null) || (altPrivate.isEmpty()) || ((original) && (altPrivate.equals("Privï¿½")))) {
/*  456 */       altPrivate = "Private";
/*  457 */       strings.set("alternate-private-tag", altPrivate);
/*      */     }
/*  459 */     altPrivate = "[" + altPrivate + "]";
/*      */     
/*  461 */     altMoreUsers = strings.getString("alternate-moreusers-tag");
/*  462 */     if ((altMoreUsers == null) || (altMoreUsers.isEmpty()) || ((original) && (altMoreUsers.equals("Autre Noms")))) {
/*  463 */       altMoreUsers = "More Users";
/*  464 */       strings.set("alternate-moreusers-tag", altMoreUsers);
/*  465 */       stringChanged = true;
/*      */     }
/*  467 */     altMoreUsers = "[" + altMoreUsers + "]";
/*      */     
/*  469 */     altEveryone = strings.getString("alternate-everyone-tag");
/*  470 */     if ((altEveryone == null) || (altEveryone.isEmpty()) || ((original) && (altEveryone.equals("Tout le Monde")))) {
/*  471 */       altEveryone = "Everyone";
/*  472 */       strings.set("alternate-everyone-tag", altEveryone);
/*  473 */       stringChanged = true;
/*      */     }
/*  475 */     altEveryone = "[" + altEveryone + "]";
/*      */     
/*  477 */     altOperators = strings.getString("alternate-operators-tag");
/*  478 */     if ((altOperators == null) || (altOperators.isEmpty()) || ((original) && (altOperators.equals("Opï¿½rateurs")))) {
/*  479 */       altOperators = "Operators";
/*  480 */       strings.set("alternate-operators-tag", altOperators);
/*  481 */       stringChanged = true;
/*      */     }
/*  483 */     altOperators = "[" + altOperators + "]";
/*      */     
/*  485 */     altTimer = strings.getString("alternate-timer-tag");
/*  486 */     if ((altTimer == null) || (altTimer.isEmpty()) || ((original) && (altTimer.equals("Minuterie")))) {
/*  487 */       altTimer = "Timer";
/*  488 */       strings.set("alternate-timer-tag", altTimer);
/*  489 */       stringChanged = true;
/*      */     }
/*      */     
/*  492 */     altFee = strings.getString("alternate-fee-tag");
/*  493 */     if ((altFee == null) || (altFee.isEmpty())) {
/*  494 */       altFee = "Fee";
/*  495 */       strings.set("alternate-fee-tag", altFee);
/*  496 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  503 */     tempString = strings.getString("msg-user-conflict-door");
/*  504 */     if (tempString == null) {
/*  505 */       strings.set("msg-user-conflict-door", "Conflicting door removed!");
/*  506 */       stringChanged = true;
/*      */     }
/*  508 */     tempString = strings.getString("msg-user-illegal");
/*  509 */     if (tempString == null) {
/*  510 */       strings.set("msg-user-illegal", "Illegal chest removed!");
/*  511 */       stringChanged = true;
/*      */     }
/*  513 */     tempString = strings.getString("msg-user-resize-owned");
/*  514 */     if (tempString == null) {
/*  515 */       strings.set("msg-user-resize-owned", "You cannot resize a chest claimed by ***.");
/*  516 */       stringChanged = true;
/*      */     }
/*  518 */     tempString = strings.getString("msg-help-chest");
/*  519 */     if (tempString == null) {
/*  520 */       strings.set("msg-help-chest", "Place a sign headed [Private] next to a chest to lock it.");
/*  521 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  526 */     tempString = strings.getString("msg-owner-release");
/*  527 */     if (tempString == null) {
/*  528 */       strings.set("msg-owner-release", "You have released a container!");
/*  529 */       stringChanged = true;
/*      */     }
/*  531 */     tempString = strings.getString("msg-admin-release");
/*  532 */     if (tempString == null) {
/*  533 */       strings.set("msg-admin-release", "(Admin) @@@ has broken open a container owned by ***!");
/*  534 */       stringChanged = true;
/*      */     }
/*  536 */     tempString = strings.getString("msg-user-release-owned");
/*  537 */     if (tempString == null) {
/*  538 */       strings.set("msg-user-release-owned", "You cannot release a container claimed by ***.");
/*  539 */       stringChanged = true;
/*      */     }
/*  541 */     tempString = strings.getString("msg-owner-remove");
/*  542 */     if (tempString == null) {
/*  543 */       strings.set("msg-owner-remove", "You have removed users from a container!");
/*  544 */       stringChanged = true;
/*      */     }
/*  546 */     tempString = strings.getString("msg-user-remove-owned");
/*  547 */     if (tempString == null) {
/*  548 */       strings.set("msg-user-remove-owned", "You cannot remove users from a container claimed by ***.");
/*  549 */       stringChanged = true;
/*      */     }
/*  551 */     tempString = strings.getString("msg-user-break-owned");
/*  552 */     if (tempString == null) {
/*  553 */       strings.set("msg-user-break-owned", "You cannot break a container claimed by ***.");
/*  554 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  559 */     tempString = strings.getString("msg-user-denied-door");
/*  560 */     if (tempString == null) {
/*  561 */       strings.set("msg-user-denied-door", "You don't have permission to use this door.");
/*  562 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  567 */     tempString = strings.getString("msg-user-touch-fee");
/*  568 */     if (tempString == null) {
/*  569 */       strings.set("msg-user-touch-fee", "A fee of ### will be paid to ***, to open.");
/*  570 */       stringChanged = true;
/*      */     }
/*  572 */     tempString = strings.getString("msg-user-touch-owned");
/*  573 */     if (tempString == null) {
/*  574 */       strings.set("msg-user-touch-owned", "This container has been claimed by ***.");
/*  575 */       stringChanged = true;
/*      */     }
/*  577 */     tempString = strings.getString("msg-help-select");
/*  578 */     if (tempString == null) {
/*  579 */       strings.set("msg-help-select", "Sign selected, use /lockette <line number> <text> to edit.");
/*  580 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  585 */     tempString = strings.getString("msg-admin-bypass");
/*  586 */     if (tempString == null) {
/*  587 */       strings.set("msg-admin-bypass", "Bypassed a door owned by ***, be sure to close it behind you.");
/*  588 */       stringChanged = true;
/*      */     }
/*  590 */     tempString = strings.getString("msg-admin-snoop");
/*  591 */     if (tempString == null) {
/*  592 */       strings.set("msg-admin-snoop", "(Admin) @@@ has snooped around in a container owned by ***!");
/*  593 */       stringChanged = true;
/*      */     }
/*  595 */     tempString = strings.getString("msg-user-denied");
/*  596 */     if (tempString == null) {
/*  597 */       strings.set("msg-user-denied", "You don't have permission to open this container.");
/*  598 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  603 */     tempString = strings.getString("msg-error-zone");
/*  604 */     if (tempString == null) {
/*  605 */       strings.set("msg-error-zone", "This zone is protected by ***.");
/*  606 */       stringChanged = true;
/*      */     }
/*  608 */     tempString = strings.getString("msg-error-permission");
/*  609 */     if (tempString == null) {
/*  610 */       strings.set("msg-error-permission", "Permission to lock container denied.");
/*  611 */       stringChanged = true;
/*  612 */     } else if (tempString.equals("Permission to lock containers denied.")) {
/*  613 */       strings.set("msg-error-permission", "Permission to lock container denied.");
/*  614 */       stringChanged = true;
/*      */     }
/*  616 */     tempString = strings.getString("msg-error-claim");
/*  617 */     if (tempString == null) {
/*  618 */       strings.set("msg-error-claim", "No unclaimed container nearby to make Private!");
/*  619 */       stringChanged = true;
/*      */     }
/*  621 */     tempString = strings.getString("msg-error-claim-conflict");
/*  622 */     if (tempString == null) {
/*  623 */       strings.set("msg-error-claim-conflict", "Conflict with an existing protected door.");
/*  624 */       stringChanged = true;
/*      */     }
/*  626 */     tempString = strings.getString("msg-admin-claim-error");
/*  627 */     if (tempString == null) {
/*  628 */       strings.set("msg-admin-claim-error", "Player *** is not online, be sure you have the correct name.");
/*  629 */       stringChanged = true;
/*      */     }
/*  631 */     tempString = strings.getString("msg-admin-claim");
/*  632 */     if (tempString == null) {
/*  633 */       strings.set("msg-admin-claim", "You have claimed a container for ***.");
/*  634 */       stringChanged = true;
/*      */     }
/*  636 */     tempString = strings.getString("msg-owner-claim");
/*  637 */     if (tempString == null) {
/*  638 */       strings.set("msg-owner-claim", "You have claimed a container!");
/*  639 */       stringChanged = true;
/*      */     }
/*  641 */     tempString = strings.getString("msg-error-adduser-owned");
/*  642 */     if (tempString == null) {
/*  643 */       strings.set("msg-error-adduser-owned", "You cannot add users to a container claimed by ***.");
/*  644 */       stringChanged = true;
/*      */     }
/*  646 */     tempString = strings.getString("msg-error-adduser");
/*  647 */     if (tempString == null) {
/*  648 */       strings.set("msg-error-adduser", "No claimed container nearby to add users to!");
/*  649 */       stringChanged = true;
/*      */     }
/*  651 */     tempString = strings.getString("msg-owner-adduser");
/*  652 */     if (tempString == null) {
/*  653 */       strings.set("msg-owner-adduser", "You have added users to a container!");
/*  654 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  659 */     if (original) {
/*  660 */       strings.set("msg-help-command1", "&C/lockette <line number> <text> - Edits signs on locked containers. Right click on the sign to edit.");
/*  661 */       strings.set("msg-help-command2", "&C/lockette fix - Fixes an automatic door that is in the wrong position. Look at the door to edit.");
/*  662 */       strings.set("msg-help-command3", "&C/lockette reload - Reloads the configuration files. Operators only.");
/*  663 */       strings.set("msg-help-command4", "&C/lockette version - Reports Lockette version.");
/*  664 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  679 */     tempString = strings.getString("msg-admin-reload");
/*  680 */     if (tempString == null) {
/*  681 */       strings.set("msg-admin-reload", "Reloading plugin configuration files.");
/*  682 */       stringChanged = true;
/*      */     }
/*  684 */     tempString = strings.getString("msg-error-fix");
/*  685 */     if (tempString == null) {
/*  686 */       strings.set("msg-error-fix", "No owned door found.");
/*  687 */       stringChanged = true;
/*      */     }
/*  689 */     tempString = strings.getString("msg-error-edit");
/*  690 */     if (tempString == null) {
/*  691 */       strings.set("msg-error-edit", "First select a sign by right clicking it.");
/*  692 */       stringChanged = true;
/*      */     }
/*  694 */     tempString = strings.getString("msg-owner-edit");
/*  695 */     if (tempString == null) {
/*  696 */       strings.set("msg-owner-edit", "Sign edited successfully.");
/*  697 */       stringChanged = true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  711 */     if ((original) && (stringChanged)) {
/*      */       try {
/*  713 */         strings.save(stringsFile);
/*      */       }
/*      */       catch (Exception localException2) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static boolean isProtected(Block block)
/*      */   {
/*  725 */     if (!enabled) { return false;
/*      */     }
/*  727 */     int type = block.getTypeId();
/*      */     
/*  729 */     if (type == Material.WALL_SIGN.getId()) {
/*  730 */       Sign sign = (Sign)block.getState();
/*  731 */       String text = ChatColor.stripColor(sign.getLine(0)).toLowerCase();
/*      */       
/*  733 */       if ((text.equals("[private]")) || (text.equalsIgnoreCase(altPrivate)))
/*  734 */         return true;
/*  735 */       if ((text.equals("[more users]")) || (text.equalsIgnoreCase(altMoreUsers))) {
/*  736 */         Block checkBlock = getSignAttachedBlock(block);
/*      */         
/*  738 */         if ((checkBlock != null) && (findBlockOwner(checkBlock) != null)) {
/*  739 */           return true;
/*      */         }
/*      */       }
/*  742 */     } else if (findBlockOwner(block) != null) { return true;
/*      */     }
/*  744 */     return false;
/*      */   }
/*      */   
/*      */   public static String getProtectedOwner(Block block)
/*      */   {
/*  749 */     return Bukkit.getOfflinePlayer(getProtectedOwnerUUID(block)).getName();
/*      */   }
/*      */   
/*      */   public static UUID getProtectedOwnerUUID(Block block) {
/*  753 */     if (!enabled) { return null;
/*      */     }
/*  755 */     int type = block.getTypeId();
/*      */     
/*  757 */     if (type == Material.WALL_SIGN.getId()) {
/*  758 */       Sign sign = (Sign)block.getState();
/*  759 */       String text = ChatColor.stripColor(sign.getLine(0)).toLowerCase();
/*      */       
/*  761 */       if ((text.equals("[private]")) || (text.equalsIgnoreCase(altPrivate)))
/*  762 */         return getUUIDFromMeta(sign, 1);
/*  763 */       if ((text.equals("[more users]")) || (text.equalsIgnoreCase(altMoreUsers))) {
/*  764 */         Block checkBlock = getSignAttachedBlock(block);
/*      */         
/*  766 */         if (checkBlock != null) {
/*  767 */           Block signBlock = findBlockOwner(checkBlock);
/*      */           
/*  769 */           if (signBlock != null) {
/*  770 */             sign = (Sign)signBlock.getState();
/*  771 */             return getUUIDFromMeta(sign, 1);
/*      */           }
/*      */         }
/*      */       }
/*      */     } else {
/*  776 */       Block signBlock = findBlockOwner(block);
/*  777 */       if (signBlock != null) {
/*  778 */         Sign sign = (Sign)signBlock.getState();
/*  779 */         return getUUIDFromMeta(sign, 1);
/*      */       }
/*      */     }
/*      */     
/*  783 */     return null;
/*      */   }
/*      */   
/*      */   public static boolean isEveryone(Block block) {
/*  787 */     if (!enabled) { return true;
/*      */     }
/*  789 */     Block signBlock = findBlockOwner(block);
/*      */     
/*  791 */     if (signBlock == null) { return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  796 */     Sign sign = (Sign)signBlock.getState();
/*      */     
/*      */ 
/*      */ 
/*  800 */     for (int y = 1; y <= 3; y++) {
/*  801 */       if (!sign.getLine(y).isEmpty()) {
/*  802 */         String line = sign.getLine(y).replaceAll("(?i)§[0-F]", "");
/*      */         
/*  804 */         if ((line.equalsIgnoreCase("[Everyone]")) || (line.equalsIgnoreCase(altEveryone))) { return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  810 */     List<Block> list = findBlockUsers(block, signBlock);
/*  811 */     int count = list.size();
/*      */     
/*  813 */     for (int x = 0; x < count; x++) {
/*  814 */       sign = (Sign)((Block)list.get(x)).getState();
/*      */       
/*  816 */       for (y = 1; y <= 3; y++) {
/*  817 */         if (!sign.getLine(y).isEmpty()) {
/*  818 */           String line = sign.getLine(y).replaceAll("(?i)§[0-F]", "");
/*      */           
/*  820 */           if ((line.equalsIgnoreCase("[Everyone]")) || (line.equalsIgnoreCase(altEveryone))) {
/*  821 */             return true;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  827 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean pluginEnableOverride(String pluginName)
/*      */   {
/*  835 */     return isInList(pluginName, disabledPluginList);
/*      */   }
/*      */   
/*      */   protected boolean usingExternalPermissions()
/*      */   {
/*  840 */     if (!usePermissions) return false;
/*  841 */     return super.usingExternalPermissions();
/*      */   }
/*      */   
/*      */ 
/*      */   protected boolean usingExternalZones()
/*      */   {
/*  847 */     return super.usingExternalZones();
/*      */   }
/*      */   
/*      */   protected String getLocalizedEveryone()
/*      */   {
/*  852 */     return altEveryone;
/*      */   }
/*      */   
/*      */   protected String getLocalizedOperators()
/*      */   {
/*  857 */     return altOperators;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void localizedMessage(Player player, String broadcast, String key)
/*      */   {
/*  866 */     localizedMessage(player, broadcast, key, null, null);
/*      */   }
/*      */   
/*      */   protected void localizedMessage(Player player, String broadcast, String key, String sub) {
/*  870 */     localizedMessage(player, broadcast, key, sub, null);
/*      */   }
/*      */   
/*      */   protected void localizedMessage(Player player, String broadcast, String key, String sub, String num) {
/*  874 */     String color = "";
/*      */     
/*      */ 
/*  877 */     if (key.startsWith("msg-user-")) {
/*  878 */       if ((broadcast == null) && (!msgUser)) return;
/*  879 */       color = ChatColor.YELLOW.toString();
/*  880 */     } else if (key.startsWith("msg-owner-")) {
/*  881 */       if ((broadcast == null) && (!msgOwner)) return;
/*  882 */       color = ChatColor.GOLD.toString();
/*  883 */     } else if (key.startsWith("msg-admin-")) {
/*  884 */       if ((broadcast == null) && (!msgAdmin)) return;
/*  885 */       color = ChatColor.RED.toString();
/*  886 */     } else if (key.startsWith("msg-error-")) {
/*  887 */       if ((broadcast == null) && (!msgError)) return;
/*  888 */       color = ChatColor.RED.toString();
/*  889 */     } else if (key.startsWith("msg-help-")) {
/*  890 */       if ((broadcast == null) && (!msgHelp)) return;
/*  891 */       color = ChatColor.GOLD.toString();
/*      */     }
/*      */     
/*      */ 
/*  895 */     String message = strings.getString(key);
/*  896 */     if ((message == null) || (message.isEmpty())) { return;
/*      */     }
/*      */     
/*  899 */     message = message.replaceAll("&([0-9A-Fa-f])", "§$1");
/*  900 */     if (sub != null) message = message.replaceAll("\\*\\*\\*", sub + color);
/*  901 */     if (num != null) message = message.replaceAll("###", num);
/*  902 */     if (player != null) { message = message.replaceAll("@@@", player.getName());
/*      */     }
/*      */     
/*  905 */     if (broadcast != null) { selectiveBroadcast(broadcast, color + "[Lockette] " + message);
/*  906 */     } else if (player != null) { player.sendMessage(color + "[Lockette] " + message);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   protected static Block findBlockOwner(Block block)
/*      */   {
/*  913 */     return findBlockOwner(block, null, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static Block findBlockOwnerBreak(Block block)
/*      */   {
/*  921 */     int type = block.getTypeId();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  926 */     if (BlockUtil.isInList(type, BlockUtil.materialListChests)) {
/*  927 */       return findBlockOwnerBase(block, null, false, false, false, false, false);
/*      */     }
/*  929 */     if ((BlockUtil.isInList(type, BlockUtil.materialListTools)) || (isInList(Integer.valueOf(type), customBlockList))) {
/*  930 */       return findBlockOwnerBase(block, null, false, false, false, false, false);
/*      */     }
/*  932 */     if ((protectTrapDoors) && (BlockUtil.isInList(type, BlockUtil.materialListTrapDoors))) {
/*  933 */       return findBlockOwnerBase(block, null, false, false, false, false, false);
/*      */     }
/*  935 */     if ((protectDoors) && (BlockUtil.isInList(type, BlockUtil.materialListDoors))) {
/*  936 */       return findBlockOwnerBase(block, null, false, true, true, false, false);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  942 */     Block checkBlock = findBlockOwnerBase(block, null, false, false, false, false, false);
/*  943 */     if (checkBlock != null) { return checkBlock;
/*      */     }
/*  945 */     if (protectTrapDoors)
/*      */     {
/*      */ 
/*      */ 
/*  949 */       checkBlock = block.getRelative(BlockFace.NORTH);
/*  950 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/*  951 */         ((checkBlock.getData() & 0x3) == 2)) {
/*  952 */         checkBlock = findBlockOwnerBase(checkBlock, null, false, false, false, false, false);
/*  953 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */       
/*  957 */       checkBlock = block.getRelative(BlockFace.EAST);
/*  958 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/*  959 */         ((checkBlock.getData() & 0x3) == 0)) {
/*  960 */         checkBlock = findBlockOwnerBase(checkBlock, null, false, false, false, false, false);
/*  961 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */       
/*  965 */       checkBlock = block.getRelative(BlockFace.SOUTH);
/*  966 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/*  967 */         ((checkBlock.getData() & 0x3) == 3)) {
/*  968 */         checkBlock = findBlockOwnerBase(checkBlock, null, false, false, false, false, false);
/*  969 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */       
/*  973 */       checkBlock = block.getRelative(BlockFace.WEST);
/*  974 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/*  975 */         ((checkBlock.getData() & 0x3) == 1)) {
/*  976 */         checkBlock = findBlockOwnerBase(checkBlock, null, false, false, false, false, false);
/*  977 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  982 */     if (protectDoors)
/*      */     {
/*      */ 
/*  985 */       checkBlock = block.getRelative(BlockFace.UP);
/*  986 */       type = checkBlock.getTypeId();
/*      */       
/*  988 */       if (!BlockUtil.isInList(type, BlockUtil.materialListDoors))
/*      */       {
/*      */ 
/*  991 */         return findBlockOwnerBase(checkBlock, null, false, true, true, false, false);
/*      */       }
/*      */     }
/*      */     
/*  995 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static Block findBlockOwner(Block block, Block ignoreBlock, boolean iterateFurther)
/*      */   {
/* 1002 */     if (block == null) {
/* 1003 */       return null;
/*      */     }
/* 1005 */     int type = block.getTypeId();
/*      */     Location ignore;
/*      */     Location ignore;
/* 1008 */     if (ignoreBlock != null) ignore = ignoreBlock.getLocation(); else {
/* 1009 */       ignore = null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1014 */     if (BlockUtil.isInList(type, BlockUtil.materialListChests)) {
/* 1015 */       return findBlockOwnerBase(block, ignore, true, false, false, false, false);
/*      */     }
/* 1017 */     if ((BlockUtil.isInList(type, BlockUtil.materialListTools)) || (isInList(Integer.valueOf(type), customBlockList))) {
/* 1018 */       return findBlockOwnerBase(block, ignore, false, false, false, false, false);
/*      */     }
/* 1020 */     if ((protectTrapDoors) && (BlockUtil.isInList(type, BlockUtil.materialListTrapDoors)))
/*      */     {
/*      */ 
/* 1023 */       return findBlockOwner(getTrapDoorAttachedBlock(block), ignoreBlock, false);
/*      */     }
/* 1025 */     if ((protectDoors) && (BlockUtil.isInList(type, BlockUtil.materialListDoors))) {
/* 1026 */       return findBlockOwnerBase(block, ignore, true, true, true, true, iterateFurther);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1032 */     if (protectTrapDoors)
/*      */     {
/*      */ 
/* 1035 */       Block checkBlock = findBlockOwnerBase(block, ignore, false, false, false, false, false);
/* 1036 */       if (checkBlock != null) { return checkBlock;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1041 */       checkBlock = block.getRelative(BlockFace.NORTH);
/* 1042 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/* 1043 */         ((checkBlock.getData() & 0x3) == 2)) {
/* 1044 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, false, false, false, false);
/* 1045 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */       
/* 1049 */       checkBlock = block.getRelative(BlockFace.EAST);
/* 1050 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/* 1051 */         ((checkBlock.getData() & 0x3) == 0)) {
/* 1052 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, false, false, false, false);
/* 1053 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */       
/* 1057 */       checkBlock = block.getRelative(BlockFace.SOUTH);
/* 1058 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/* 1059 */         ((checkBlock.getData() & 0x3) == 3)) {
/* 1060 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, false, false, false, false);
/* 1061 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */       
/* 1065 */       checkBlock = block.getRelative(BlockFace.WEST);
/* 1066 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListTrapDoors)) && 
/* 1067 */         ((checkBlock.getData() & 0x3) == 1)) {
/* 1068 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, false, false, false, false);
/* 1069 */         if (checkBlock != null) { return checkBlock;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1074 */     if (protectDoors)
/*      */     {
/*      */ 
/* 1077 */       Block checkBlock = block.getRelative(BlockFace.UP);
/* 1078 */       type = checkBlock.getTypeId();
/* 1079 */       if (BlockUtil.isInList(type, BlockUtil.materialListDoors))
/*      */       {
/*      */ 
/* 1082 */         Block result = findBlockOwnerBase(checkBlock, ignore, true, true, true, true, iterateFurther);
/* 1083 */         if (result != null) { return result;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1088 */       checkBlock = block.getRelative(BlockFace.DOWN);
/* 1089 */       type = checkBlock.getTypeId();
/* 1090 */       if (BlockUtil.isInList(type, BlockUtil.materialListDoors))
/*      */       {
/*      */ 
/*      */ 
/* 1094 */         Block checkBlock2 = checkBlock.getRelative(BlockFace.DOWN);
/* 1095 */         type = checkBlock2.getTypeId();
/* 1096 */         if (BlockUtil.isInList(type, BlockUtil.materialListDoors)) {
/* 1097 */           return findBlockOwnerBase(checkBlock2, ignore, true, true, false, true, iterateFurther);
/*      */         }
/* 1099 */         return findBlockOwnerBase(checkBlock, ignore, true, true, false, true, iterateFurther);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1104 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Block findBlockOwnerBase(Block block, Location ignore, boolean iterate, boolean iterateUp, boolean iterateDown, boolean includeEnds, boolean iterateFurther)
/*      */   {
/* 1119 */     if (iterateUp) {
/* 1120 */       Block checkBlock = block.getRelative(BlockFace.UP);
/* 1121 */       int type = checkBlock.getTypeId();
/*      */       
/* 1123 */       if (BlockUtil.isInList(type, BlockUtil.materialListDoors)) {
/* 1124 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, iterateUp, false, includeEnds, false);
/* 1125 */       } else if (includeEnds)
/* 1126 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, false, false, includeEnds, false); else {
/* 1127 */         checkBlock = null;
/*      */       }
/* 1129 */       if (checkBlock != null) { return checkBlock;
/*      */       }
/*      */     }
/* 1132 */     if (iterateDown) {
/* 1133 */       Block checkBlock = block.getRelative(BlockFace.DOWN);
/* 1134 */       int type = checkBlock.getTypeId();
/*      */       
/* 1136 */       if (BlockUtil.isInList(type, BlockUtil.materialListDoors)) {
/* 1137 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, false, iterateDown, includeEnds, false);
/* 1138 */       } else if (includeEnds)
/* 1139 */         checkBlock = findBlockOwnerBase(checkBlock, ignore, false, false, false, includeEnds, false); else {
/* 1140 */         checkBlock = null;
/*      */       }
/* 1142 */       if (checkBlock != null) { return checkBlock;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1151 */     Block checkBlock = block.getRelative(BlockFace.NORTH);
/* 1152 */     if (checkBlock.getTypeId() == Material.WALL_SIGN.getId()) {
/* 1153 */       byte face = checkBlock.getData();
/* 1154 */       if (face == BlockUtil.faceList[2]) {
/*      */         boolean doCheck;
/*      */         boolean doCheck;
/* 1157 */         if (ignore == null) { doCheck = true; } else { boolean doCheck;
/* 1158 */           if (checkBlock.getLocation().equals(ignore)) doCheck = false; else
/* 1159 */             doCheck = true;
/*      */         }
/* 1161 */         if (doCheck) {
/* 1162 */           Sign sign = (Sign)checkBlock.getState();
/* 1163 */           String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */           
/* 1165 */           if ((text.equals("[private]")) || (text.equalsIgnoreCase(altPrivate))) return checkBlock;
/*      */         }
/*      */       }
/* 1168 */     } else if ((iterate) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1169 */       checkBlock = findBlockOwnerBase(checkBlock, ignore, iterateFurther, iterateUp, iterateDown, includeEnds, false);
/* 1170 */       if (checkBlock != null) { return checkBlock;
/*      */       }
/*      */     }
/* 1173 */     checkBlock = block.getRelative(BlockFace.EAST);
/* 1174 */     if (checkBlock.getTypeId() == Material.WALL_SIGN.getId()) {
/* 1175 */       byte face = checkBlock.getData();
/* 1176 */       if (face == BlockUtil.faceList[3]) {
/*      */         boolean doCheck;
/*      */         boolean doCheck;
/* 1179 */         if (ignore == null) { doCheck = true; } else { boolean doCheck;
/* 1180 */           if (checkBlock.getLocation().equals(ignore)) doCheck = false; else
/* 1181 */             doCheck = true;
/*      */         }
/* 1183 */         if (doCheck) {
/* 1184 */           Sign sign = (Sign)checkBlock.getState();
/* 1185 */           String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */           
/* 1187 */           if ((text.equals("[private]")) || (text.equalsIgnoreCase(altPrivate))) return checkBlock;
/*      */         }
/*      */       }
/* 1190 */     } else if ((iterate) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1191 */       checkBlock = findBlockOwnerBase(checkBlock, ignore, iterateFurther, iterateUp, iterateDown, includeEnds, false);
/* 1192 */       if (checkBlock != null) { return checkBlock;
/*      */       }
/*      */     }
/* 1195 */     checkBlock = block.getRelative(BlockFace.SOUTH);
/* 1196 */     if (checkBlock.getTypeId() == Material.WALL_SIGN.getId()) {
/* 1197 */       byte face = checkBlock.getData();
/* 1198 */       if (face == BlockUtil.faceList[0]) {
/*      */         boolean doCheck;
/*      */         boolean doCheck;
/* 1201 */         if (ignore == null) { doCheck = true; } else { boolean doCheck;
/* 1202 */           if (checkBlock.getLocation().equals(ignore)) doCheck = false; else
/* 1203 */             doCheck = true;
/*      */         }
/* 1205 */         if (doCheck) {
/* 1206 */           Sign sign = (Sign)checkBlock.getState();
/* 1207 */           String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */           
/* 1209 */           if ((text.equals("[private]")) || (text.equalsIgnoreCase(altPrivate))) return checkBlock;
/*      */         }
/*      */       }
/* 1212 */     } else if ((iterate) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1213 */       checkBlock = findBlockOwnerBase(checkBlock, ignore, iterateFurther, iterateUp, iterateDown, includeEnds, false);
/* 1214 */       if (checkBlock != null) { return checkBlock;
/*      */       }
/*      */     }
/* 1217 */     checkBlock = block.getRelative(BlockFace.WEST);
/* 1218 */     if (checkBlock.getTypeId() == Material.WALL_SIGN.getId()) {
/* 1219 */       byte face = checkBlock.getData();
/* 1220 */       if (face == BlockUtil.faceList[1]) {
/*      */         boolean doCheck;
/*      */         boolean doCheck;
/* 1223 */         if (ignore == null) { doCheck = true; } else { boolean doCheck;
/* 1224 */           if (checkBlock.getLocation().equals(ignore)) doCheck = false; else
/* 1225 */             doCheck = true;
/*      */         }
/* 1227 */         if (doCheck) {
/* 1228 */           Sign sign = (Sign)checkBlock.getState();
/* 1229 */           String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */           
/* 1231 */           if ((text.equals("[private]")) || (text.equalsIgnoreCase(altPrivate))) return checkBlock;
/*      */         }
/*      */       }
/* 1234 */     } else if ((iterate) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1235 */       checkBlock = findBlockOwnerBase(checkBlock, ignore, iterateFurther, iterateUp, iterateDown, includeEnds, false);
/* 1236 */       if (checkBlock != null) { return checkBlock;
/*      */       }
/*      */     }
/* 1239 */     return null;
/*      */   }
/*      */   
/*      */   protected static List<Block> findBlockUsers(Block block, Block signBlock)
/*      */   {
/* 1244 */     int type = block.getTypeId();
/*      */     
/* 1246 */     if (BlockUtil.isInList(type, BlockUtil.materialListChests))
/* 1247 */       return findBlockUsersBase(block, true, false, false, false, 0);
/* 1248 */     if ((protectTrapDoors) && (BlockUtil.isInList(type, BlockUtil.materialListTrapDoors))) {
/* 1249 */       return findBlockUsersBase(getTrapDoorAttachedBlock(block), false, false, false, true, 0);
/*      */     }
/* 1251 */     if ((protectDoors) && (BlockUtil.isInList(type, BlockUtil.materialListDoors))) {
/* 1252 */       return findBlockUsersBase(block, true, true, true, false, signBlock.getY());
/*      */     }
/* 1254 */     return findBlockUsersBase(block, false, false, false, false, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static List<Block> findBlockUsersBase(Block block, boolean iterate, boolean iterateUp, boolean iterateDown, boolean traps, int includeYPos)
/*      */   {
/* 1262 */     List<Block> list = new ArrayList();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1267 */     if (iterateUp) {
/* 1268 */       Block checkBlock = block.getRelative(BlockFace.UP);
/* 1269 */       int type = checkBlock.getTypeId();
/*      */       
/* 1271 */       if (BlockUtil.isInList(type, BlockUtil.materialListDoors)) {
/* 1272 */         list.addAll(findBlockUsersBase(checkBlock, false, iterateUp, false, false, includeYPos));
/*      */ 
/*      */       }
/* 1275 */       else if (checkBlock.getY() == includeYPos) {
/* 1276 */         list.addAll(findBlockUsersBase(checkBlock, false, false, false, false, includeYPos));
/*      */       }
/*      */     }
/* 1279 */     if (iterateDown) {
/* 1280 */       Block checkBlock = block.getRelative(BlockFace.DOWN);
/* 1281 */       int type = checkBlock.getTypeId();
/*      */       
/* 1283 */       if (BlockUtil.isInList(type, BlockUtil.materialListDoors)) {
/* 1284 */         list.addAll(findBlockUsersBase(checkBlock, false, false, iterateDown, false, includeYPos));
/*      */       }
/*      */       else {
/* 1287 */         list.addAll(findBlockUsersBase(checkBlock, false, false, false, false, includeYPos));
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1293 */     Block checkBlock = block.getRelative(BlockFace.NORTH);
/* 1294 */     int type = checkBlock.getTypeId();
/* 1295 */     if (type == Material.WALL_SIGN.getId()) {
/* 1296 */       byte face = checkBlock.getData();
/* 1297 */       if (face == BlockUtil.faceList[2]) {
/* 1298 */         Sign sign = (Sign)checkBlock.getState();
/* 1299 */         String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */         
/* 1301 */         if ((text.equals("[more users]")) || (text.equalsIgnoreCase(altMoreUsers))) list.add(checkBlock);
/*      */       }
/* 1303 */     } else if (iterate) {
/* 1304 */       if (type == block.getTypeId()) {
/* 1305 */         list.addAll(findBlockUsersBase(checkBlock, false, iterateUp, iterateDown, false, includeYPos));
/*      */       }
/* 1307 */     } else if ((traps) && (BlockUtil.isInList(type, BlockUtil.materialListTrapDoors))) {
/* 1308 */       byte face = checkBlock.getData();
/* 1309 */       if ((face & 0x3) == 2) {
/* 1310 */         list.addAll(findBlockUsersBase(checkBlock, false, false, false, false, includeYPos));
/*      */       }
/*      */     }
/*      */     
/* 1314 */     checkBlock = block.getRelative(BlockFace.EAST);
/* 1315 */     type = checkBlock.getTypeId();
/* 1316 */     if (type == Material.WALL_SIGN.getId()) {
/* 1317 */       byte face = checkBlock.getData();
/* 1318 */       if (face == BlockUtil.faceList[3]) {
/* 1319 */         Sign sign = (Sign)checkBlock.getState();
/* 1320 */         String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */         
/* 1322 */         if ((text.equals("[more users]")) || (text.equalsIgnoreCase(altMoreUsers))) list.add(checkBlock);
/*      */       }
/* 1324 */     } else if (iterate) {
/* 1325 */       if (type == block.getTypeId()) {
/* 1326 */         list.addAll(findBlockUsersBase(checkBlock, false, iterateUp, iterateDown, false, includeYPos));
/*      */       }
/* 1328 */     } else if ((traps) && (BlockUtil.isInList(type, BlockUtil.materialListTrapDoors))) {
/* 1329 */       byte face = checkBlock.getData();
/* 1330 */       if ((face & 0x3) == 0) {
/* 1331 */         list.addAll(findBlockUsersBase(checkBlock, false, false, false, false, includeYPos));
/*      */       }
/*      */     }
/*      */     
/* 1335 */     checkBlock = block.getRelative(BlockFace.SOUTH);
/* 1336 */     type = checkBlock.getTypeId();
/* 1337 */     if (type == Material.WALL_SIGN.getId()) {
/* 1338 */       byte face = checkBlock.getData();
/* 1339 */       if (face == BlockUtil.faceList[0]) {
/* 1340 */         Sign sign = (Sign)checkBlock.getState();
/* 1341 */         String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */         
/* 1343 */         if ((text.equals("[more users]")) || (text.equalsIgnoreCase(altMoreUsers))) list.add(checkBlock);
/*      */       }
/* 1345 */     } else if (iterate) {
/* 1346 */       if (type == block.getTypeId()) {
/* 1347 */         list.addAll(findBlockUsersBase(checkBlock, false, iterateUp, iterateDown, false, includeYPos));
/*      */       }
/* 1349 */     } else if ((traps) && (BlockUtil.isInList(type, BlockUtil.materialListTrapDoors))) {
/* 1350 */       byte face = checkBlock.getData();
/* 1351 */       if ((face & 0x3) == 3) {
/* 1352 */         list.addAll(findBlockUsersBase(checkBlock, false, false, false, false, includeYPos));
/*      */       }
/*      */     }
/*      */     
/* 1356 */     checkBlock = block.getRelative(BlockFace.WEST);
/* 1357 */     type = checkBlock.getTypeId();
/* 1358 */     if (type == Material.WALL_SIGN.getId()) {
/* 1359 */       byte face = checkBlock.getData();
/* 1360 */       if (face == BlockUtil.faceList[1]) {
/* 1361 */         Sign sign = (Sign)checkBlock.getState();
/* 1362 */         String text = sign.getLine(0).replaceAll("(?i)§[0-F]", "").toLowerCase();
/*      */         
/* 1364 */         if ((text.equals("[more users]")) || (text.equalsIgnoreCase(altMoreUsers))) list.add(checkBlock);
/*      */       }
/* 1366 */     } else if (iterate) {
/* 1367 */       if (type == block.getTypeId()) {
/* 1368 */         list.addAll(findBlockUsersBase(checkBlock, false, iterateUp, iterateDown, false, includeYPos));
/*      */       }
/* 1370 */     } else if ((traps) && (BlockUtil.isInList(type, BlockUtil.materialListTrapDoors))) {
/* 1371 */       byte face = checkBlock.getData();
/* 1372 */       if ((face & 0x3) == 1) {
/* 1373 */         list.addAll(findBlockUsersBase(checkBlock, false, false, false, false, includeYPos));
/*      */       }
/*      */     }
/*      */     
/* 1377 */     return list;
/*      */   }
/*      */   
/*      */   protected static int findChestCountNear(Block block)
/*      */   {
/* 1382 */     return findChestCountNearBase(block, (byte)0);
/*      */   }
/*      */   
/*      */   private static int findChestCountNearBase(Block block, byte face)
/*      */   {
/* 1387 */     int count = 0;
/*      */     
/*      */ 
/*      */ 
/* 1391 */     if (face != 2) {
/* 1392 */       Block checkBlock = block.getRelative(BlockFace.NORTH);
/* 1393 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1394 */         count++;
/* 1395 */         if (face == 0) { count += findChestCountNearBase(checkBlock, (byte)3);
/*      */         }
/*      */       }
/*      */     }
/* 1399 */     if (face != 5) {
/* 1400 */       Block checkBlock = block.getRelative(BlockFace.EAST);
/* 1401 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1402 */         count++;
/* 1403 */         if (face == 0) { count += findChestCountNearBase(checkBlock, (byte)4);
/*      */         }
/*      */       }
/*      */     }
/* 1407 */     if (face != 3) {
/* 1408 */       Block checkBlock = block.getRelative(BlockFace.SOUTH);
/* 1409 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1410 */         count++;
/* 1411 */         if (face == 0) { count += findChestCountNearBase(checkBlock, (byte)2);
/*      */         }
/*      */       }
/*      */     }
/* 1415 */     if (face != 4) {
/* 1416 */       Block checkBlock = block.getRelative(BlockFace.WEST);
/* 1417 */       if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1418 */         count++;
/* 1419 */         if (face == 0) { count += findChestCountNearBase(checkBlock, (byte)5);
/*      */         }
/*      */       }
/*      */     }
/* 1423 */     return count;
/*      */   }
/*      */   
/*      */ 
/*      */   protected static void rotateChestOrientation(Block block, BlockFace blockFace)
/*      */   {
/* 1429 */     if (!BlockUtil.isInList(block.getTypeId(), BlockUtil.materialListChests)) return;
/* 1430 */     if ((!rotateChests) && (block.getData() != 0)) {
/*      */       return;
/*      */     }
/*      */     byte face;
/* 1434 */     if (blockFace == BlockFace.NORTH) { face = BlockUtil.faceList[2]; } else { byte face;
/* 1435 */       if (blockFace == BlockFace.EAST) { face = BlockUtil.faceList[3]; } else { byte face;
/* 1436 */         if (blockFace == BlockFace.SOUTH) { face = BlockUtil.faceList[0]; } else { byte face;
/* 1437 */           if (blockFace == BlockFace.WEST) face = BlockUtil.faceList[1]; else {
/*      */             return;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     byte face;
/* 1444 */     Block checkBlock = block.getRelative(BlockFace.NORTH);
/* 1445 */     if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1446 */       if ((face == BlockUtil.faceList[1]) || (face == BlockUtil.faceList[3])) {
/* 1447 */         block.setData(face);
/* 1448 */         checkBlock.setData(face);
/*      */       }
/* 1450 */       return;
/*      */     }
/*      */     
/* 1453 */     checkBlock = block.getRelative(BlockFace.EAST);
/* 1454 */     if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1455 */       if ((face == BlockUtil.faceList[2]) || (face == BlockUtil.faceList[0])) {
/* 1456 */         block.setData(face);
/* 1457 */         checkBlock.setData(face);
/*      */       }
/* 1459 */       return;
/*      */     }
/*      */     
/* 1462 */     checkBlock = block.getRelative(BlockFace.SOUTH);
/* 1463 */     if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1464 */       if ((face == BlockUtil.faceList[1]) || (face == BlockUtil.faceList[3])) {
/* 1465 */         block.setData(face);
/* 1466 */         checkBlock.setData(face);
/*      */       }
/* 1468 */       return;
/*      */     }
/*      */     
/* 1471 */     checkBlock = block.getRelative(BlockFace.WEST);
/* 1472 */     if ((BlockUtil.isInList(checkBlock.getTypeId(), BlockUtil.materialListChests)) && (checkBlock.getTypeId() == block.getTypeId())) {
/* 1473 */       if ((face == BlockUtil.faceList[2]) || (face == BlockUtil.faceList[0])) {
/* 1474 */         block.setData(face);
/* 1475 */         checkBlock.setData(face);
/*      */       }
/* 1477 */       return;
/*      */     }
/*      */     
/* 1480 */     block.setData(face);
/*      */   }
/*      */   
/*      */ 
/*      */   protected static List<Block> toggleDoors(Block block, Block keyBlock, boolean wooden, boolean trap)
/*      */   {
/* 1486 */     List<Block> list = new ArrayList();
/*      */     
/* 1488 */     toggleDoorBase(block, keyBlock, !trap, wooden, list);
/*      */     try {
/* 1490 */       if (!wooden) { block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
/*      */       }
/*      */     }
/*      */     catch (NoSuchFieldError localNoSuchFieldError) {}catch (NoSuchMethodError localNoSuchMethodError) {}catch (NoClassDefFoundError localNoClassDefFoundError) {}
/*      */     
/*      */ 
/* 1496 */     return list;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static void toggleSingleDoor(Block block)
/*      */   {
/* 1503 */     int type = block.getTypeId();
/*      */     
/*      */ 
/* 1506 */     if (BlockUtil.isInList(type, BlockUtil.materialListJustDoors)) {
/* 1507 */       toggleDoorBase(block, null, true, false, null);
/* 1508 */     } else if ((BlockUtil.isInList(type, BlockUtil.materialListTrapDoors)) || 
/* 1509 */       (BlockUtil.isInList(type, BlockUtil.materialListGates))) {
/* 1510 */       toggleDoorBase(block, null, false, false, null);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected static void toggleHalfDoor(Block block, boolean effect)
/*      */   {
/* 1518 */     int type = block.getTypeId();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1523 */     if (BlockUtil.isInList(type, BlockUtil.materialListDoors)) {
/* 1524 */       block.setData((byte)(block.getData() ^ 0x4));
/*      */       try {
/* 1526 */         if (effect) { block.getWorld().playEffect(block.getLocation(), Effect.DOOR_TOGGLE, 0);
/*      */         }
/*      */       }
/*      */       catch (NoSuchFieldError localNoSuchFieldError) {}catch (NoSuchMethodError localNoSuchMethodError) {}catch (NoClassDefFoundError localNoClassDefFoundError) {}
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void toggleDoorBase(Block block, Block keyBlock, boolean iterateUpDown, boolean skipDoor, List<Block> list)
/*      */   {
/* 1541 */     if (list != null) list.add(block);
/* 1542 */     if (!skipDoor) { block.setData((byte)(block.getData() ^ 0x4));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1547 */     if (iterateUpDown) {
/* 1548 */       Block checkBlock = block.getRelative(BlockFace.UP);
/* 1549 */       if (checkBlock.getTypeId() == block.getTypeId()) { toggleDoorBase(checkBlock, null, false, skipDoor, list);
/*      */       }
/* 1551 */       checkBlock = block.getRelative(BlockFace.DOWN);
/* 1552 */       if (checkBlock.getTypeId() == block.getTypeId()) { toggleDoorBase(checkBlock, null, false, skipDoor, list);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1558 */     if (keyBlock != null) {
/* 1559 */       Block checkBlock = block.getRelative(BlockFace.NORTH);
/* 1560 */       if ((checkBlock.getTypeId() == block.getTypeId()) && (
/* 1561 */         ((checkBlock.getX() == keyBlock.getX()) && (checkBlock.getZ() == keyBlock.getZ())) || (
/* 1562 */         (block.getX() == keyBlock.getX()) && (block.getZ() == keyBlock.getZ())))) {
/* 1563 */         toggleDoorBase(checkBlock, null, true, false, list);
/*      */       }
/*      */       
/*      */ 
/* 1567 */       checkBlock = block.getRelative(BlockFace.EAST);
/* 1568 */       if ((checkBlock.getTypeId() == block.getTypeId()) && (
/* 1569 */         ((checkBlock.getX() == keyBlock.getX()) && (checkBlock.getZ() == keyBlock.getZ())) || (
/* 1570 */         (block.getX() == keyBlock.getX()) && (block.getZ() == keyBlock.getZ())))) {
/* 1571 */         toggleDoorBase(checkBlock, null, true, false, list);
/*      */       }
/*      */       
/*      */ 
/* 1575 */       checkBlock = block.getRelative(BlockFace.SOUTH);
/* 1576 */       if ((checkBlock.getTypeId() == block.getTypeId()) && (
/* 1577 */         ((checkBlock.getX() == keyBlock.getX()) && (checkBlock.getZ() == keyBlock.getZ())) || (
/* 1578 */         (block.getX() == keyBlock.getX()) && (block.getZ() == keyBlock.getZ())))) {
/* 1579 */         toggleDoorBase(checkBlock, null, true, false, list);
/*      */       }
/*      */       
/*      */ 
/* 1583 */       checkBlock = block.getRelative(BlockFace.WEST);
/* 1584 */       if ((checkBlock.getTypeId() == block.getTypeId()) && (
/* 1585 */         ((checkBlock.getX() == keyBlock.getX()) && (checkBlock.getZ() == keyBlock.getZ())) || (
/* 1586 */         (block.getX() == keyBlock.getX()) && (block.getZ() == keyBlock.getZ())))) {
/* 1587 */         toggleDoorBase(checkBlock, null, true, false, list);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static int getSignOption(Block signBlock, String tag, String altTag, int defaultValue)
/*      */   {
/* 1599 */     Sign sign = (Sign)signBlock.getState();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1607 */     for (int y = 2; y <= 3; y++) {
/* 1608 */       if (!sign.getLine(y).isEmpty()) {
/* 1609 */         String line = sign.getLine(y).replaceAll("(?i)§[0-F]", "");
/*      */         
/*      */ 
/* 1612 */         int end = line.length() - 1;
/*      */         
/* 1614 */         if ((end >= 2) && (line.charAt(0) == '[') && (line.charAt(end) == ']')) {
/* 1615 */           int index = line.indexOf(":");
/*      */           
/* 1617 */           if (index == -1)
/*      */           {
/* 1619 */             if ((line.substring(1, end).equalsIgnoreCase(tag)) || (line.substring(1, end).equalsIgnoreCase(altTag))) {
/* 1620 */               return defaultValue;
/*      */             }
/*      */             
/*      */           }
/* 1624 */           else if ((line.substring(1, index).equalsIgnoreCase(tag)) || (line.substring(1, index).equalsIgnoreCase(altTag)))
/*      */           {
/*      */ 
/* 1627 */             for (int x = index; x < end; x++) {
/* 1628 */               if (Character.isDigit(line.charAt(x))) {
/* 1629 */                 index = x;
/* 1630 */                 break;
/*      */               }
/*      */             }
/* 1633 */             for (x = index + 1; x < end; x++) {
/* 1634 */               if (!Character.isDigit(line.charAt(x))) {
/* 1635 */                 end = x;
/* 1636 */                 break;
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */             try
/*      */             {
/* 1643 */               return Integer.parseInt(line.substring(index, end));
/*      */             }
/*      */             catch (NumberFormatException ex) {
/* 1646 */               return defaultValue;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1653 */     return defaultValue;
/*      */   }
/*      */   
/*      */   protected static boolean isInList(Object target, List<Object> list)
/*      */   {
/* 1658 */     if (list == null) return false;
/* 1659 */     for (int x = 0; x < list.size(); x++) if (list.get(x).equals(target)) return true;
/* 1660 */     return false;
/*      */   }
/*      */   
/*      */   private static boolean isHackFormat(String line) {
/* 1664 */     String[] strs = line.split(":");
/* 1665 */     return (line.indexOf(":") > 1) && (strs[1].length() == 36);
/*      */   }
/*      */   
/*      */   private static String trim(String str) {
/* 1669 */     return str == null ? null : str.trim();
/*      */   }
/*      */   
/*      */   private static String getPlayerName(String str)
/*      */   {
/* 1674 */     return trim(str.indexOf(":") > 0 ? str.split(":")[0] : str);
/*      */   }
/*      */   
/*      */   private static String getPlayerUUIDString(String str) {
/* 1678 */     return trim(str.indexOf(":") > 0 ? str.split(":")[1] : str);
/*      */   }
/*      */   
/*      */   private static UUID getPlayerUUID(String str) {
/* 1682 */     return UUID.fromString(getPlayerUUIDString(str));
/*      */   }
/*      */   
/*      */   static void setLine(Sign sign, int index, String typed)
/*      */   {
/* 1687 */     OfflinePlayer player = null;
/* 1688 */     if ((!typed.isEmpty()) && (typed.indexOf("[") != 0)) {
/* 1689 */       String id = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', typed));
/* 1690 */       player = Bukkit.getOfflinePlayer(id);
/*      */     }
/*      */     
/*      */ 
/* 1694 */     setLine(sign, index, typed, player);
/*      */   }
/*      */   
/*      */   static void setLine(Sign sign, int index, String typed, OfflinePlayer player)
/*      */   {
/* 1699 */     String cline = ChatColor.translateAlternateColorCodes('&', typed);
/* 1700 */     if (DEBUG) {
/* 1701 */       System.out.println("[Lockette](setLine) cline : " + cline);
/*      */     }
/* 1703 */     sign.setLine(index, cline);
/* 1704 */     sign.update(true);
/*      */     
/* 1706 */     UUID[] uuids = null;
/* 1707 */     if ((!sign.hasMetadata("LocketteUUIDs")) || (sign.getMetadata("LocketteUUIDs").size() < 1)) {
/* 1708 */       uuids = new UUID[3];
/* 1709 */       sign.setMetadata("LocketteUUIDs", new org.bukkit.metadata.FixedMetadataValue(plugin, uuids));
/*      */     } else {
/* 1711 */       List<MetadataValue> list = sign.getMetadata("LocketteUUIDs");
/*      */       
/* 1713 */       uuids = (UUID[])((MetadataValue)list.get(0)).value();
/*      */     }
/* 1715 */     uuids[(index - 1)] = (player != null ? player.getUniqueId() : null);
/* 1716 */     if (DEBUG) {
/* 1717 */       log.info("[Lockette] setting the line " + index + " to " + cline);
/* 1718 */       log.info("[Lockette] corresponding player is " + player);
/* 1719 */       log.info("[Lockette] uuid has been attached: " + uuids[(index - 1)]);
/*      */     }
/*      */   }
/*      */   
/*      */   private static UUID getUUIDFromMeta(Sign sign, int index) {
/* 1724 */     if ((sign.hasMetadata("LocketteUUIDs")) && (sign.getMetadata("LocketteUUIDs").size() > 0)) {
/* 1725 */       List<MetadataValue> list = sign.getMetadata("LocketteUUIDs");
/*      */       
/* 1727 */       UUID uuid = ((UUID[])(UUID[])((MetadataValue)list.get(0)).value())[(index - 1)];
/* 1728 */       if (DEBUG) {
/* 1729 */         log.info("[Lockette] uuid : " + uuid);
/*      */       }
/* 1731 */       return uuid;
/*      */     }
/* 1733 */     return null;
/*      */   }
/*      */   
/*      */   static void removeUUIDMetadata(Sign sign) {
/* 1737 */     if (sign.hasMetadata("LocketteUUIDs")) {
/* 1738 */       sign.removeMetadata("LocketteUUIDs", plugin);
/*      */     }
/*      */   }
/*      */   
/*      */   private static boolean oldFormatCheck(String signname, String pname) {
/* 1743 */     signname = ChatColor.stripColor(signname);
/* 1744 */     pname = ChatColor.stripColor(pname);
/* 1745 */     int length = pname.length();
/* 1746 */     if (length > 16)
/* 1747 */       length = 16;
/* 1748 */     return signname.equalsIgnoreCase(pname.substring(0, length));
/*      */   }
/*      */   
/*      */   private static boolean matchUserUUID(Sign sign, int index, OfflinePlayer player, boolean update) {
/*      */     try {
/* 1753 */       String line = sign.getLine(index);
/* 1754 */       String checkline = ChatColor.stripColor(line);
/*      */       
/* 1756 */       if (DEBUG) {
/* 1757 */         log.info("[Lockette] matchUserUUID : checkline = " + checkline);
/*      */       }
/*      */       
/* 1760 */       if (((checkline.indexOf("[") == 0) && (checkline.indexOf("]") > 1)) || 
/* 1761 */         (line.isEmpty())) {
/* 1762 */         return false;
/*      */       }
/*      */       
/*      */ 
/* 1766 */       if (!uuidSupport) {
/* 1767 */         if (DEBUG) {
/* 1768 */           log.info("[Lockette] NO UUID support, doing old name checking.");
/*      */         }
/*      */         
/* 1771 */         String pname = player.getName();
/* 1772 */         String against = checkline.split(":")[0].trim();
/* 1773 */         return oldFormatCheck(against, pname);
/*      */       }
/*      */       
/* 1776 */       UUID uuid = null;
/* 1777 */       name = getPlayerName(line);
/* 1778 */       if (DEBUG) {
/* 1779 */         log.info("[Lockette] Name on the sign is : " + name);
/*      */       }
/*      */       
/* 1782 */       if (isHackFormat(line))
/*      */       {
/*      */         try
/*      */         {
/* 1786 */           uuid = getPlayerUUID(line);
/*      */         } catch (IllegalArgumentException e) {
/* 1788 */           log.info("[" + plugin.getDescription().getName() + "] Invalid Player UUID!");
/* 1789 */           return false;
/*      */         }
/* 1791 */         if ((uuid != null) && (update)) {
/* 1792 */           OfflinePlayer p = Bukkit.getOfflinePlayer(uuid);
/* 1793 */           if (DEBUG) {
/* 1794 */             log.info("[Lockette] updating the old hacked format for " + p);
/*      */           }
/* 1796 */           setLine(sign, index, name, p);
/*      */         }
/*      */         
/* 1799 */         sign.update();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/* 1804 */       if (DEBUG) {
/* 1805 */         log.info("[Lockette] hasMeta? : " + sign.hasMetadata("LocketteUUIDs"));
/* 1806 */         log.info("[Lockette] uuid ? : " + getUUIDFromMeta(sign, index));
/*      */       }
/*      */       
/* 1809 */       if ((!sign.hasMetadata("LocketteUUIDs")) || (getUUIDFromMeta(sign, index) == null)) {
/* 1810 */         if (DEBUG) {
/* 1811 */           log.info("[Lockette] Checking for original format for " + checkline);
/*      */         }
/* 1813 */         OfflinePlayer oplayer = Bukkit.getOfflinePlayer(checkline);
/* 1814 */         if ((oplayer != null) && (oplayer.hasPlayedBefore())) {
/* 1815 */           if (DEBUG) {
/* 1816 */             log.info("[Lockette] converting original format for " + oplayer + " name = " + checkline);
/*      */           }
/* 1818 */           setLine(sign, index, line, oplayer);
/*      */         }
/*      */         else {
/* 1821 */           pname = player.getName();
/* 1822 */           String against = checkline.split(":")[0].trim();
/* 1823 */           if (oldFormatCheck(against, pname)) {
/* 1824 */             if (DEBUG) {
/* 1825 */               log.info("[Lockette] Partial match! Converting original format for " + player.getName() + " name = " + checkline);
/*      */             }
/* 1827 */             setLine(sign, index, player.getName(), player);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1841 */         sign.update();
/*      */       }
/*      */       
/* 1844 */       uuid = getUUIDFromMeta(sign, index);
/*      */       
/* 1846 */       if (DEBUG) {
/* 1847 */         log.info("[Lockette] uuid on the sign = " + uuid);
/* 1848 */         log.info("[Lockette] player's uuid    = " + player.getUniqueId());
/*      */       }
/*      */       
/* 1851 */       if (uuid != null)
/*      */       {
/* 1853 */         OfflinePlayer oplayer = Bukkit.getOfflinePlayer(uuid);
/* 1854 */         if (!oplayer.hasPlayedBefore()) {
/* 1855 */           if (DEBUG) {
/* 1856 */             log.info("[Lockette] removing bad UUID");
/*      */           }
/* 1858 */           removeUUIDMetadata(sign);
/*      */         } else {
/* 1860 */           if (uuid.equals(player.getUniqueId()))
/*      */           {
/* 1862 */             if (!ChatColor.stripColor(ChatColor.stripColor(name)).equals(player.getName())) {
/* 1863 */               if (DEBUG) {
/* 1864 */                 log.info("[Lockette] setting name : " + player.getName());
/*      */               }
/*      */               
/* 1867 */               sign.setLine(index, player.getName());
/* 1868 */               sign.update();
/*      */             }
/* 1870 */             if (DEBUG) {
/* 1871 */               log.info("[Lockette] uuid equal");
/*      */             }
/*      */             
/* 1874 */             return true;
/*      */           }
/* 1876 */           if (DEBUG) {
/* 1877 */             log.info("[Lockette] reset name to " + oplayer.getName());
/*      */           }
/*      */           
/* 1880 */           sign.setLine(index, oplayer.getName());
/* 1881 */           sign.update();
/*      */         }
/*      */       }
/*      */       else {
/* 1885 */         List<String> names = getPreviousNames(player.getUniqueId());
/* 1886 */         for (String n : names)
/* 1887 */           if (n.equalsIgnoreCase(name)) {
/* 1888 */             if (DEBUG) {
/* 1889 */               log.info("[Lockette] Found the match in the name history!");
/*      */             }
/*      */             
/* 1892 */             setLine(sign, index, player.getName(), player);
/* 1893 */             return true;
/*      */           }
/*      */       }
/*      */     } catch (Exception e) { String name;
/*      */       String pname;
/* 1898 */       log.info("[Lockette] Something bad happened returning match = false");
/* 1899 */       e.printStackTrace();
/*      */     }
/*      */     
/* 1902 */     return false;
/*      */   }
/*      */   
/*      */   public static boolean isOwner(Block block, String name)
/*      */   {
/* 1907 */     return isOwner(block, Bukkit.getOfflinePlayer(name));
/*      */   }
/*      */   
/*      */   @Deprecated
/*      */   public static boolean isUser(Block block, String name, boolean withGroups) {
/* 1912 */     return isUser(block, Bukkit.getOfflinePlayer(name), withGroups);
/*      */   }
/*      */   
/*      */   public static boolean isOwner(Block block, OfflinePlayer player) {
/* 1916 */     if (!enabled) {
/* 1917 */       return true;
/*      */     }
/* 1919 */     Block checkBlock = findBlockOwner(block);
/* 1920 */     if (checkBlock == null) {
/* 1921 */       return true;
/*      */     }
/* 1923 */     Sign sign = (Sign)checkBlock.getState();
/*      */     
/*      */ 
/* 1926 */     return matchUserUUID(sign, 1, player, true);
/*      */   }
/*      */   
/*      */   public static boolean isOwner(Sign sign, OfflinePlayer player)
/*      */   {
/* 1931 */     return matchUserUUID(sign, 1, player, true);
/*      */   }
/*      */   
/*      */   public static boolean isUser(Block block, OfflinePlayer player, boolean withGroups) {
/* 1935 */     if (!enabled) {
/* 1936 */       return true;
/*      */     }
/* 1938 */     Block signBlock = findBlockOwner(block);
/*      */     
/* 1940 */     if (signBlock == null) {
/* 1941 */       return true;
/*      */     }
/*      */     
/* 1944 */     Sign sign = (Sign)signBlock.getState();
/*      */     String line;
/* 1946 */     for (int y = 1; y <= 3; y++) {
/* 1947 */       line = sign.getLine(y);
/* 1948 */       if (matchUserUUID(sign, y, player, true)) {
/* 1949 */         return true;
/*      */       }
/*      */       
/* 1952 */       if ((withGroups) && 
/* 1953 */         (plugin.inGroup(block.getWorld(), player.getName(), line))) {
/* 1954 */         return true;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1959 */     List<Block> list = findBlockUsers(block, signBlock);
/* 1960 */     for (Block blk : list) {
/* 1961 */       sign = (Sign)blk.getState();
/*      */       
/* 1963 */       for (int y = 1; y <= 3; y++) {
/* 1964 */         String line = sign.getLine(y);
/* 1965 */         if (matchUserUUID(sign, y, player, true)) {
/* 1966 */           return true;
/*      */         }
/*      */         
/*      */ 
/* 1970 */         if ((withGroups) && 
/* 1971 */           (plugin.inGroup(block.getWorld(), player.getName(), line))) {
/* 1972 */           return true;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1977 */     return false;
/*      */   }
/*      */   
/*      */ 
/* 1981 */   private static String NAME_HISTORY_URL = "https://api.mojang.com/user/profiles/";
/* 1982 */   private static final JSONParser jsonParser = new JSONParser();
/*      */   
/*      */   private static List<String> getPreviousNames(UUID uuid) {
/* 1985 */     String name = null;
/* 1986 */     List<String> list = new ArrayList();
/*      */     try
/*      */     {
/* 1989 */       if (name == null) {
/* 1990 */         HttpURLConnection connection = (HttpURLConnection)new URL(NAME_HISTORY_URL + uuid.toString().replace("-", "") + "/names").openConnection();
/* 1991 */         connection.setConnectTimeout(5000);
/* 1992 */         connection.setReadTimeout(5000);
/* 1993 */         JSONArray array = (JSONArray)jsonParser.parse(new java.io.InputStreamReader(connection.getInputStream()));
/*      */         
/* 1995 */         Iterator<JSONObject> iterator = array.iterator();
/* 1996 */         while (iterator.hasNext()) {
/* 1997 */           JSONObject obj = (JSONObject)iterator.next();
/* 1998 */           list.add((String)obj.get("name"));
/*      */         }
/*      */       }
/*      */     } catch (SocketTimeoutException ste) {
/* 2002 */       log.info("[Lockette] Connection timeout (to Mojang site)");
/*      */     } catch (Exception ioe) {
/* 2004 */       log.info("[Lockette] Failed to get Name history!");
/*      */     }
/* 2006 */     return list;
/*      */   }
/*      */ }
