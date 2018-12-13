package com.arlania.world.content.skill.impl.smithing.interfaces;

import com.arlania.world.entity.impl.player.Player;

import java.util.Arrays;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 * Created by Stan van der Bend on 14/09/2017.
 */
public class SmithingInterface {
    
    private final static int AMOUNT_OF_COLUMNS = 5;
    private final static int AMOUNT_OF_ITEMS_A_COLUMN = 5;

    private final static int TOTAL_AMOUNT_OF_ITEM_SLOTS = AMOUNT_OF_COLUMNS * AMOUNT_OF_ITEMS_A_COLUMN;

    private final static int[] COLUMNS = new int[AMOUNT_OF_COLUMNS];

    private final static int[]
            STRING_BARS_MAPPING = new int[TOTAL_AMOUNT_OF_ITEM_SLOTS],
            STRING_DESC_MAPPING = new int[TOTAL_AMOUNT_OF_ITEM_SLOTS];

    static {
        COLUMNS[0] = 1119;
        COLUMNS[1] = 1120;
        COLUMNS[2] = 1121;
        COLUMNS[3] = 1122;
        COLUMNS[4] = 1123;

        STRING_BARS_MAPPING[0] = 1125;STRING_DESC_MAPPING[0] = 1094;
        STRING_BARS_MAPPING[1] = 1124;STRING_DESC_MAPPING[1] = 1085;
        STRING_BARS_MAPPING[2] = 1116;STRING_DESC_MAPPING[2] = 1087;
        STRING_BARS_MAPPING[3] = 1089;STRING_DESC_MAPPING[3] = 1086;
        STRING_BARS_MAPPING[4] = 1090;STRING_DESC_MAPPING[4] = 1088;
        STRING_BARS_MAPPING[5] = 1126;STRING_DESC_MAPPING[5] = 1091;
        STRING_BARS_MAPPING[6] = 1129;STRING_DESC_MAPPING[6] = 1093;
        STRING_BARS_MAPPING[7] = 1118;STRING_DESC_MAPPING[7] = 1083;
        STRING_BARS_MAPPING[8] = 1095;STRING_DESC_MAPPING[8] = 1092;
        STRING_BARS_MAPPING[9] = 8428;STRING_DESC_MAPPING[9] = 8429;
        STRING_BARS_MAPPING[10] = 1109;STRING_DESC_MAPPING[10] = 1098;
        STRING_BARS_MAPPING[11] = 1110;STRING_DESC_MAPPING[11] = 1099;
        STRING_BARS_MAPPING[12] = 1111;STRING_DESC_MAPPING[12] = 1100;
        STRING_BARS_MAPPING[13] = 1112;STRING_DESC_MAPPING[13] = 1101;
        STRING_BARS_MAPPING[14] = 11459;STRING_DESC_MAPPING[14] = 11461;
        STRING_BARS_MAPPING[15] = 1127;STRING_DESC_MAPPING[15] = 1102;
        STRING_BARS_MAPPING[16] = 1113;STRING_DESC_MAPPING[16] = 1103;
        STRING_BARS_MAPPING[17] = 1114;STRING_DESC_MAPPING[17] = 1104;
        STRING_BARS_MAPPING[18] = 1115;STRING_DESC_MAPPING[18] = 1105;
        STRING_BARS_MAPPING[19] = 13357;STRING_DESC_MAPPING[19] = 13358;
        STRING_BARS_MAPPING[20] = 1128;STRING_DESC_MAPPING[20] = 1107;
        STRING_BARS_MAPPING[21] = 1130;STRING_DESC_MAPPING[21] = 1108;
        STRING_BARS_MAPPING[22] = 1131;STRING_DESC_MAPPING[22] = 1106;
        STRING_BARS_MAPPING[23] = 1132;STRING_DESC_MAPPING[23] = 1096;
        STRING_BARS_MAPPING[24] = 1135;STRING_DESC_MAPPING[24] = 1134;
    }

    public static void open(final Player player, final SmithingType type){

        //First clean the interface of cached data in the client.
        clean(player);

        //Then send the new interface data to the client.
        type.drawBoxes(player);

        //Then open the background interface for the previously drawn data.
        player.getPacketSender().sendInterface(994);

    }
    
    public static void clean(final Player player){
        Arrays.stream(STRING_BARS_MAPPING).forEach(emptyLine(player));
        Arrays.stream(STRING_DESC_MAPPING).forEach(emptyLine(player));
        Arrays.stream(COLUMNS).forEach(emptyBoxes(player));
    }

    private static IntConsumer emptyBoxes(final Player player) {
        return column -> IntStream.range(0, 5).forEach(emptyBox(player, column));
    }
    private static IntConsumer emptyBox(final Player player, final int column) {
        return slot -> player.getPacketSender().sendSmithingData(-1, slot, column, -1);
    }
    private static IntConsumer emptyLine(final Player player) {
        return id -> player.getPacketSender().sendString(id, "");
    }
    
    public static int getSlot(int index, int column){
        final int multiplier = column-COLUMNS[0];
        final int amount_to_detract = multiplier * AMOUNT_OF_ITEMS_A_COLUMN;
        return index - amount_to_detract;
    }

    static int getColumnId(int index){
        if(index < 5)
            return COLUMNS[0];
        else if(index < 10)
            return COLUMNS[1];
        else if(index < 15)
            return COLUMNS[2];
        else if(index < 20)
            return COLUMNS[3];
        else if(index < 25)
            return COLUMNS[4];
        else return -1;
    }

    static int getStringBarsId(int index){
        return STRING_BARS_MAPPING[index];
    }
    static int getStringDescId(int index){
        return STRING_DESC_MAPPING[index];
    }

}