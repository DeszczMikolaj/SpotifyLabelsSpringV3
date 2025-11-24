import { useState } from 'react';
import { Search, Music } from 'lucide-react';
import { Input } from './ui/input';
import { Track, Label } from '../App';
import { TrackItem } from './TrackItem';

type SearchPanelProps = {
  labels: Label[];
  onToggleLabel: (trackId: string, labelId: string) => void;
  onAddTrack: (track: Track) => void;
  tracks: Track[];
};

// Mock search results
const mockSearchResults: Track[] = [
  {
    id: 'search-1',
    name: 'As It Was',
    artist: 'Harry Styles',
    album: "Harry's House",
    duration: '2:47',
    imageUrl: 'https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=300&h=300&fit=crop',
    labels: [],
  },
  {
    id: 'search-2',
    name: 'Anti-Hero',
    artist: 'Taylor Swift',
    album: 'Midnights',
    duration: '3:20',
    imageUrl: 'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=300&h=300&fit=crop',
    labels: [],
  },
  {
    id: 'search-3',
    name: 'Flowers',
    artist: 'Miley Cyrus',
    album: 'Endless Summer Vacation',
    duration: '3:20',
    imageUrl: 'https://images.unsplash.com/photo-1459749411175-04bf5292ceea?w=300&h=300&fit=crop',
    labels: [],
  },
  {
    id: 'search-4',
    name: 'Cruel Summer',
    artist: 'Taylor Swift',
    album: 'Lover',
    duration: '2:58',
    imageUrl: 'https://images.unsplash.com/photo-1614613535308-eb5fbd3d2c17?w=300&h=300&fit=crop',
    labels: [],
  },
  {
    id: 'search-5',
    name: 'Someone You Loved',
    artist: 'Lewis Capaldi',
    album: 'Divinely Uninspired to a Hellish Extent',
    duration: '3:02',
    imageUrl: 'https://images.unsplash.com/photo-1511379938547-c1f69419868d?w=300&h=300&fit=crop',
    labels: [],
  },
];

export function SearchPanel({ labels, onToggleLabel, onAddTrack, tracks }: SearchPanelProps) {
  const [searchQuery, setSearchQuery] = useState('');
  const [searchResults, setSearchResults] = useState<Track[]>([]);
  const [hasSearched, setHasSearched] = useState(false);

  const handleSearch = (query: string) => {
    setSearchQuery(query);
    if (query.trim()) {
      setHasSearched(true);
      // Merge search results with existing tracks to maintain label state
      const mergedResults = mockSearchResults.map(result => {
        const existingTrack = tracks.find(t => t.id === result.id);
        return existingTrack || result;
      });
      setSearchResults(mergedResults);
    } else {
      setSearchResults([]);
      setHasSearched(false);
    }
  };

  const handleToggleLabel = (trackId: string, labelId: string) => {
    // Check if track exists in main tracks array
    const existingTrack = tracks.find(t => t.id === trackId);
    
    if (!existingTrack) {
      // Add track to main collection first
      const searchTrack = searchResults.find(t => t.id === trackId);
      if (searchTrack) {
        onAddTrack({ ...searchTrack, labels: [labelId] });
        // Update local search results
        setSearchResults(searchResults.map(t => 
          t.id === trackId ? { ...t, labels: [labelId] } : t
        ));
      }
    } else {
      // Toggle label on existing track
      onToggleLabel(trackId, labelId);
      // Update local search results
      setSearchResults(searchResults.map(t => {
        if (t.id === trackId) {
          const hasLabel = t.labels.includes(labelId);
          return {
            ...t,
            labels: hasLabel
              ? t.labels.filter(id => id !== labelId)
              : [...t.labels, labelId],
          };
        }
        return t;
      }));
    }
  };

  return (
    <div className="p-8">
      <div className="max-w-7xl">
        <div className="mb-8">
          <h2 className="mb-2">Search Songs</h2>
          <p className="text-white/60 mb-6">Search for songs on Spotify and add labels</p>

          <div className="relative max-w-2xl">
            <Search className="absolute left-4 top-1/2 -translate-y-1/2 size-5 text-white/40" />
            <Input
              value={searchQuery}
              onChange={(e) => handleSearch(e.target.value)}
              placeholder="Search for songs, artists, or albums..."
              className="pl-12 h-12 bg-white/10 border-white/10 text-white placeholder:text-white/40 focus:bg-white/15"
            />
          </div>
        </div>

        {hasSearched && (
          <div className="bg-black/20 rounded-lg">
            <div className="grid grid-cols-[auto,1fr,1fr,auto,auto] gap-4 px-6 py-3 border-b border-white/10 text-white/60 text-sm">
              <div className="w-8">#</div>
              <div>TITLE</div>
              <div>ALBUM</div>
              <div>LABELS</div>
              <div className="w-16 text-right">TIME</div>
            </div>

            <div className="divide-y divide-white/5">
              {searchResults.map((track, index) => (
                <TrackItem
                  key={track.id}
                  track={track}
                  index={index}
                  labels={labels}
                  onToggleLabel={handleToggleLabel}
                />
              ))}
            </div>

            {searchResults.length === 0 && (
              <div className="text-center py-16 text-white/40">
                <Music className="size-12 mx-auto mb-4 opacity-20" />
                <p>No results found for "{searchQuery}"</p>
              </div>
            )}
          </div>
        )}

        {!hasSearched && (
          <div className="text-center py-16 text-white/40">
            <Search className="size-12 mx-auto mb-4 opacity-20" />
            <p>Search for songs to add labels</p>
          </div>
        )}
      </div>
    </div>
  );
}
