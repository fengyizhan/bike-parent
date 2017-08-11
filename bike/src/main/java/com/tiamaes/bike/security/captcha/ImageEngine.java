package com.tiamaes.bike.security.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.ImageFilter;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.UniColorBackgroundGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformation;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.GlyphsPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.GlyphsVisitors;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.HorizontalSpaceGlyphsVisitor;
import com.octo.captcha.component.image.textpaster.glyphsvisitor.TranslateAllToRandomPointVisitor;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class ImageEngine extends ListImageCaptchaEngine {

	@Override
	protected void buildInitialFactories() {
		int minWordLength = 4;
		int maxWordLength = 4;
		int fontSize = 24;
		int imageWidth = 60;
		int imageHeight = 32;

		// word generator
//		WordGenerator dictionnaryWords = new ComposeDictionaryWordGenerator(new FileDictionary("toddlist"));
		WordGenerator dictionnaryWords = new RandomWordGenerator("0123456789");

		// word2image components
//		TextPaster randomPaster = new DecoratedRandomTextPaster(minWordLength,maxWordLength, 
//				,
//				//new TextDecorator[] { new LineTextDecorator(2, Color.LIGHT_GRAY)}
//				null );
		
//		@SuppressWarnings("deprecation")
//		RandomTextPaster randomPaster = new RandomTextPaster(minWordLength, maxWordLength, new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }),true);
//		GlyphsVisitors[] glyphsVisitors = new GlyphsVisitors[] { new TranslateGlyphsVerticalRandomVisitor(1), new OverlapGlyphsUsingShapeVisitor(3), new TranslateAllToRandomPointVisitor()}
		GlyphsVisitors[] glyphsVisitors = new GlyphsVisitors[] { new HorizontalSpaceGlyphsVisitor(3), new TranslateAllToRandomPointVisitor(4, 4)};
		TextPaster randomPaster = new GlyphsPaster(minWordLength, maxWordLength,
				new RandomListColorGenerator(new Color[] { new Color(23, 170, 27), new Color(220, 34, 11), new Color(23, 67, 172) }), glyphsVisitors);
		BackgroundGenerator background = new UniColorBackgroundGenerator(imageWidth, imageHeight, Color.LIGHT_GRAY);
		FontGenerator font = new RandomFontGenerator(fontSize, fontSize, new Font[] { new Font("Arial", Font.PLAIN, fontSize)});

		PinchFilter pinch2 = new PinchFilter();
		pinch2.setAmount(-.6f);
		pinch2.setRadius(0.7f);
		pinch2.setAngle((float) (Math.PI / 16));
		pinch2.setCentreX(0.3f);
		pinch2.setCentreY(1.01f);
		pinch2.setEdgeAction(ImageFunction2D.CLAMP);
		
		ImageDeformation postDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation backDef = new ImageDeformationByFilters(new ImageFilter[] {});
		ImageDeformation textDef = new ImageDeformationByFilters(new ImageFilter[] {});

		WordToImage word2image = new DeformedComposedWordToImage(font, background, randomPaster, backDef, textDef, postDef);
		addFactory(new GimpyFactory(dictionnaryWords, word2image));
	}

}
