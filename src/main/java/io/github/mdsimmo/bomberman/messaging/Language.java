package io.github.mdsimmo.bomberman.messaging;

import io.github.mdsimmo.bomberman.Bomberman;
import io.github.mdsimmo.bomberman.Config;
import io.github.mdsimmo.bomberman.utils.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Language implements Formattable {

	private static Map<String, Language> langs = new HashMap<>();
	private static Plugin plugin = Bomberman.instance;
	
	public static Language getLanguage( String lang ) {
		if ( lang == null )
			return getLanguage( "english" );
		// case insensitive match
		lang = lang.toLowerCase();
		
		// first, look in the cache
		Language language = langs.get( lang );
		if ( language != null ) {
			return language;
		} else {
			// try to load it from a file
			if ( getFile( lang ).exists() ) {
				language = new Language( lang );
				langs.put( lang, language );
				return language;
			} else {
				// if asking for English
				if ( lang.isEmpty() ) {
					language = getLanguage( "english" );
					langs.put( lang, language );
					return language;
				} else if ( lang.equalsIgnoreCase( "english" ) ) {
					language = new EnglishLanguage();
					langs.put( "english", language );
					return language;
				} else {
					// no language
					return null;
				}
			}
		}
	}

	public static void reload() {
		langs.clear();
	}
	
	private static File getFile( String lang ) {
		return new File( plugin.getDataFolder(), lang.toLowerCase() + ".lang" );
	}

	public static List<String> allLanguages() {
		File dir = plugin.getDataFolder();
		File[] files = dir.listFiles( new FilenameFilter() {
			@Override
			public boolean accept( File file, String name ) {
				return name.endsWith( ".lang" );
			}
		} );
		List<String> langs = new ArrayList<String>( files.length );
		for ( File f : files ) {
			langs.add( Utils.getFileTitle( f.getName() ) );
		}
		if ( !langs.contains( "english" ) )
			langs.add( "english" );
		return langs;
	}

	private final YamlConfiguration save;
	private final String name;
	
	private Language( String lang ) {
		File f = getFile( lang );
		save = YamlConfiguration.loadConfiguration( f );
		name = lang;
	}
	
	public String name() {
		return name;
	}
	
	public String translate( Phrase phrase ) {
		String t = save.getString( phrase.getPath() );
		if ( t == null ) {
			Language bup = getLanguage( (String)Config.LANGUAGE.getValue( save ) );
			if ( bup == null || bup == this )
				return phrase.getDefault();
			else
				return bup.translate( phrase );
		} else
			return t;
	}
	
	public boolean contains( Phrase phrase ) {
		return save.contains( phrase.getPath() );
	}

	@Override
	public String format( Message message, List<String> args ) {
		return name;
	}
	
	private static class EnglishLanguage extends Language {
		public EnglishLanguage() {
			super( "english" );
		}
		
		public String translate( Phrase phrase ) {
			return phrase.getDefault();
		}
		
		@Override
		public boolean contains( Phrase phrase ) {
			return true;
		}
	}

}
