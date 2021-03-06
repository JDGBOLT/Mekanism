package mekanism.client.gui;

import mekanism.api.Coord4D;
import mekanism.common.IRedstoneControl;
import mekanism.common.PacketHandler;
import mekanism.common.PacketHandler.Transmission;
import mekanism.common.network.PacketSimpleGui;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.ResourceType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiConfigurationTab extends GuiElement
{
	public GuiConfigurationTab(GuiMekanism gui, TileEntity tile, ResourceLocation def)
	{
		super(MekanismUtils.getResource(ResourceType.GUI_ELEMENT, "GuiConfigurationTab.png"), gui, tile, def);
	}
	
	@Override
	public void renderBackground(int xAxis, int yAxis, int guiWidth, int guiHeight)
	{
		mc.renderEngine.bindTexture(RESOURCE);
		
		guiObj.drawTexturedModalRect(guiWidth - 26, guiHeight + 6, 0, 0, 26, 26);
		
		if(xAxis >= -21 && xAxis <= -3 && yAxis >= 10 && yAxis <= 28)
		{
			guiObj.drawTexturedModalRect(guiWidth - 21, guiHeight + 10, 26, 0, 18, 18);
		}
		else {
			guiObj.drawTexturedModalRect(guiWidth - 21, guiHeight + 10, 26, 18, 18, 18);
		}
		
		mc.renderEngine.bindTexture(defaultLocation);
	}
	
	@Override
	public void renderForeground(int xAxis, int yAxis)
	{
		mc.renderEngine.bindTexture(RESOURCE);
		
		if(xAxis >= -21 && xAxis <= -3 && yAxis >= 10 && yAxis <= 28)
		{
			displayTooltip(MekanismUtils.localize("gui.configuration"), xAxis, yAxis);
		}
		
		mc.renderEngine.bindTexture(defaultLocation);
	}
	
	@Override
	public void preMouseClicked(int xAxis, int yAxis, int button) {}
	
	@Override
	public void mouseClicked(int xAxis, int yAxis, int button)
	{
		IRedstoneControl control = (IRedstoneControl)tileEntity;
		
		if(button == 0)
		{
			if(xAxis >= -21 && xAxis <= -3 && yAxis >= 10 && yAxis <= 28)
			{
				PacketHandler.sendPacket(Transmission.SERVER, new PacketSimpleGui().setParams(Coord4D.get(tileEntity), 9));
				mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
			}
		}
	}
}
