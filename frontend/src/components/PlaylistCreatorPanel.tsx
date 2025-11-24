import { useState } from 'react';
import { Plus, Music, X } from 'lucide-react';
import { Track, Label } from '../App';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Label as LabelComponent } from './ui/label';
import { Checkbox } from './ui/checkbox';
import { RadioGroup, RadioGroupItem } from './ui/radio-group';

type PlaylistCreatorPanelProps = {
  tracks: Track[];
  labels: Label[];
};

type FilterMode = 'any' | 'all';

export function PlaylistCreatorPanel({ tracks, labels }: PlaylistCreatorPanelProps) {
  const [playlistName, setPlaylistName] = useState('');
  const [selectedLabels, setSelectedLabels] = useState<string[]>([]);
  const [filterMode, setFilterMode] = useState<FilterMode>('any');
  const [isCreating, setIsCreating] = useState(false);

  const toggleLabel = (labelId: string) => {
    setSelectedLabels(prev =>
      prev.includes(labelId)
        ? prev.filter(id => id !== labelId)
        : [...prev, labelId]
    );
  };

  // Filter tracks based on the selected mode
  const filteredTracks = selectedLabels.length > 0
    ? tracks.filter(track => {
        if (filterMode === 'any') {
          // Track must have at least one of the selected labels
          return track.labels.some(labelId => selectedLabels.includes(labelId));
        } else {
          // Track must have all of the selected labels
          return selectedLabels.every(labelId => track.labels.includes(labelId));
        }
      })
    : [];

  const handleCreatePlaylist = () => {
    if (playlistName.trim() && filteredTracks.length > 0) {
      setIsCreating(true);
      // Simulate API call
      setTimeout(() => {
        alert(`Playlist "${playlistName}" created with ${filteredTracks.length} tracks!`);
        setPlaylistName('');
        setSelectedLabels([]);
        setIsCreating(false);
      }, 1000);
    }
  };

  return (
    <div className="p-8">
      <div className="max-w-7xl">
        <div className="mb-8">
          <h2 className="mb-2">Playlist Creator</h2>
          <p className="text-white/60">Create playlists from your labeled tracks</p>
        </div>

        <div className="grid lg:grid-cols-2 gap-8">
          {/* Left Column - Configuration */}
          <div className="space-y-6">
            <div className="bg-white/5 rounded-lg p-6">
              <div className="space-y-4">
                <div className="space-y-2">
                  <LabelComponent htmlFor="playlist-name">Playlist Name</LabelComponent>
                  <Input
                    id="playlist-name"
                    value={playlistName}
                    onChange={(e) => setPlaylistName(e.target.value)}
                    placeholder="Enter playlist name"
                    className="bg-[#3E3E3E] border-white/10 text-white placeholder:text-white/40"
                  />
                </div>

                <div className="space-y-3">
                  <LabelComponent>Select Labels to Include</LabelComponent>
                  <p className="text-sm text-white/60">
                    Choose which labels to use for filtering tracks
                  </p>
                  
                  {labels.length > 0 ? (
                    <div className="space-y-2 max-h-64 overflow-y-auto">
                      {labels.map((label) => (
                        <label
                          key={label.id}
                          className="flex items-center gap-3 cursor-pointer hover:bg-white/5 p-3 rounded transition-colors"
                        >
                          <Checkbox
                            checked={selectedLabels.includes(label.id)}
                            onCheckedChange={() => toggleLabel(label.id)}
                            className="border-white/40 data-[state=unchecked]:bg-white/10"
                          />
                          <div
                            className="size-5 rounded-full"
                            style={{ backgroundColor: label.color }}
                          />
                          <span className="flex-1">{label.name}</span>
                          <span className="text-sm text-white/40">
                            {tracks.filter(t => t.labels.includes(label.id)).length} tracks
                          </span>
                        </label>
                      ))}
                    </div>
                  ) : (
                    <p className="text-white/40 text-sm py-4">
                      No labels available. Create labels first!
                    </p>
                  )}
                </div>

                {selectedLabels.length > 1 && (
                  <div className="space-y-3 pt-4 border-t border-white/10">
                    <LabelComponent>Matching Mode</LabelComponent>
                    <RadioGroup value={filterMode} onValueChange={(value) => setFilterMode(value as FilterMode)}>
                      <div className="space-y-3">
                        <label className={`flex items-start gap-3 cursor-pointer p-3 rounded transition-colors border-2 ${
                          filterMode === 'any' 
                            ? 'bg-[#1DB954]/20 border-[#1DB954] hover:bg-[#1DB954]/30' 
                            : 'border-white/10 hover:bg-white/5'
                        }`}>
                          <RadioGroupItem value="any" id="mode-any" className="mt-0.5 hidden" />
                          <div className="flex-1">
                            <div className="mb-1">Match any label</div>
                            <p className="text-sm text-white/60">
                              Include tracks that have <strong>at least one</strong> of the selected labels
                            </p>
                          </div>
                        </label>
                        
                        <label className={`flex items-start gap-3 cursor-pointer p-3 rounded transition-colors border-2 ${
                          filterMode === 'all' 
                            ? 'bg-[#1DB954]/20 border-[#1DB954] hover:bg-[#1DB954]/30' 
                            : 'border-white/10 hover:bg-white/5'
                        }`}>
                          <RadioGroupItem value="all" id="mode-all" className="mt-0.5 hidden" />
                          <div className="flex-1">
                            <div className="mb-1">Match all labels</div>
                            <p className="text-sm text-white/60">
                              Include only tracks that have <strong>every</strong> selected label
                            </p>
                          </div>
                        </label>
                      </div>
                    </RadioGroup>
                  </div>
                )}
              </div>
            </div>

            <Button
              onClick={handleCreatePlaylist}
              disabled={!playlistName.trim() || filteredTracks.length === 0 || isCreating}
              className="w-full bg-[#1DB954] hover:bg-[#1ED760] text-black gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              <Plus className="size-5" />
              {isCreating ? 'Creating...' : 'Create Playlist'}
            </Button>

            {selectedLabels.length > 0 && (
              <div className="text-center text-white/60 text-sm">
                {filteredTracks.length} track{filteredTracks.length !== 1 ? 's' : ''} will be added
              </div>
            )}
          </div>

          {/* Right Column - Preview */}
          <div className="bg-white/5 rounded-lg p-6">
            <h3 className="mb-4">Preview</h3>

            {selectedLabels.length > 0 ? (
              <div className="space-y-4">
                <div className="flex flex-wrap gap-2 mb-4">
                  {selectedLabels.map(labelId => {
                    const label = labels.find(l => l.id === labelId);
                    if (!label) return null;
                    return (
                      <div
                        key={label.id}
                        className="flex items-center gap-2 bg-white/10 rounded-full px-3 py-1"
                      >
                        <div
                          className="size-3 rounded-full"
                          style={{ backgroundColor: label.color }}
                        />
                        <span className="text-sm">{label.name}</span>
                        <button
                          onClick={() => toggleLabel(label.id)}
                          className="hover:text-red-500"
                        >
                          <X className="size-3" />
                        </button>
                      </div>
                    );
                  })}
                </div>

                <div className="space-y-2 max-h-96 overflow-y-auto">
                  {filteredTracks.map((track) => (
                    <div
                      key={track.id}
                      className="flex items-center gap-3 p-3 bg-white/5 rounded hover:bg-white/10 transition-colors"
                    >
                      <img
                        src={track.imageUrl}
                        alt={track.name}
                        className="size-12 rounded"
                      />
                      <div className="flex-1 min-w-0">
                        <div className="truncate">{track.name}</div>
                        <div className="text-sm text-white/60 truncate">
                          {track.artist}
                        </div>
                      </div>
                      <div className="flex items-center gap-2">
                        <div className="flex gap-1">
                          {track.labels.filter(labelId => selectedLabels.includes(labelId)).map(labelId => {
                            const label = labels.find(l => l.id === labelId);
                            if (!label) return null;
                            return (
                              <div
                                key={label.id}
                                className="size-4 rounded-full"
                                style={{ backgroundColor: label.color }}
                                title={label.name}
                              />
                            );
                          })}
                        </div>
                        <div className="text-white/60 text-sm">{track.duration}</div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ) : (
              <div className="text-center py-16 text-white/40">
                <Music className="size-12 mx-auto mb-4 opacity-20" />
                <p>Select labels to preview tracks</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}