package fi.dy.masa.malilib.gui;

import java.util.regex.Pattern;
import com.google.common.base.Predicate;
import net.minecraft.client.gui.FontRenderer;

public class GuiTextFieldInteger extends GuiTextFieldGeneric
{
    private static final Pattern PATTER_NUMBER = Pattern.compile("-?[0-9]*");

    public GuiTextFieldInteger(int id, int x, int y, int width, int height, FontRenderer fontrenderer)
    {
        super(id, fontrenderer, x, y, width, height);

        this.setValidator(new Predicate<String>()
        {
            @Override
            public boolean apply(String input)
            {
                if (input.length() > 0 && PATTER_NUMBER.matcher(input).matches() == false)
                {
                    return false;
                }

                return true;
            }
        });
    }

}