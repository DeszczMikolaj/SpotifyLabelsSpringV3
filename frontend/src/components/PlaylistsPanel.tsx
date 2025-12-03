import { useState } from 'react';
import { useEffect } from 'react';
import { Play, Music, Tag, Tags, Search, X } from 'lucide-react';
import { Track, Label, Playlist } from '../App';
import { TrackItem } from './TrackItem';
import { Button } from './ui/button';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from './ui/dialog';
import { Input } from './ui/input';
import api from "../api/api";


type PlaylistsPanelProps = {
  tracks: Track[];
  labels: Label[];
  onToggleLabel: (trackId: string, labelId: string) => void;
};

// Mock playlists data
// const mockPlaylists: Playlist[] = [
//   {
//     id: '1',
//     name: 'My Favorites',
//     tracksCount: 45,
//     imageUrl: 'https://images.unsplash.com/photo-1470225620780-dba8ba36b745?w=300&h=300&fit=crop',
//   },
// ];

export function PlaylistsPanel({ tracks, labels, onToggleLabel }: PlaylistsPanelProps) {
  const [playlists, setPlaylists] = useState<Playlist[]>([])
  const [selectedPlaylist, setSelectedPlaylist] = useState<Playlist | null>(null);
  const [selectedTracks, setSelectedTracks] = useState<string[]>([]);
  const [isBulkDialogOpen, setIsBulkDialogOpen] = useState(false);
  const [bulkMode, setBulkMode] = useState<'selected' | 'all'>('selected');
  const [searchQuery, setSearchQuery] = useState('');

    useEffect(() => {
        setPlaylists([]);
        api
                .get("/api/spotify/mySavedTracks")
                .then((response) => {
                    setPlaylists(prev => [...prev, response.data]);
                })
                .catch((error) => {
                  console.error("API error:", error);
                });

            }, []);

  const handleToggleSelect = (trackId: string) => {
    setSelectedTracks(prev =>
      prev.includes(trackId)
        ? prev.filter(id => id !== trackId)
        : [...prev, trackId]
    );
  };

  const handleBulkLabelToggle = (labelId: string) => {
    const targetTracks = bulkMode === 'all' ? tracks : tracks.filter(t => selectedTracks.includes(t.id));
    targetTracks.forEach(track => {
      onToggleLabel(track.id, labelId);
    });
  };

  const handleBulkLabelAdd = (labelId: string) => {
    const targetTracks = bulkMode === 'all' ? tracks : tracks.filter(t => selectedTracks.includes(t.id));
    
    // Apply to all tracks that don't have the label
    targetTracks.forEach((track, index) => {
      if (!track.labels.includes(labelId)) {
        // Use queueMicrotask to ensure each state update is processed
        queueMicrotask(() => onToggleLabel(track.id, labelId));
      }
    });
  };

  const handleBulkLabelRemove = (labelId: string) => {
    const targetTracks = bulkMode === 'all' ? tracks : tracks.filter(t => selectedTracks.includes(t.id));
    
    // Apply to all tracks that have the label
    targetTracks.forEach((track, index) => {
      if (track.labels.includes(labelId)) {
        // Use queueMicrotask to ensure each state update is processed
        queueMicrotask(() => onToggleLabel(track.id, labelId));
      }
    });
  };

  const openBulkDialog = (mode: 'selected' | 'all') => {
    setBulkMode(mode);
    setIsBulkDialogOpen(true);
  };

  // Filter labels based on search query
  const filteredLabels = labels.filter(label =>
    label.name.toLowerCase().includes(searchQuery.toLowerCase())
  );

  // For bulk operations, calculate which labels can be assigned/removed
  const targetTracks = bulkMode === 'all' ? tracks : tracks.filter(t => selectedTracks.includes(t.id));
  
  // Labels that can be assigned: at least one track doesn't have it
  const labelsToAssign = filteredLabels.filter(label => 
    targetTracks.some(track => !track.labels.includes(label.id))
  );
  
  // Labels that can be removed: at least one track has it
  const labelsToRemove = filteredLabels.filter(label => 
    targetTracks.some(track => track.labels.includes(label.id))
  );

  return (
    <div className="p-8">
      {!selectedPlaylist ? (
        <div className="max-w-6xl">
          <div className="mb-8">
            <h2 className="mb-2">Your Playlists</h2>
            <p className="text-white/60">Select a playlist to label its tracks</p>
          </div>

          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-6">
            {playlists.map((playlist) => (
              <button
                key={playlist.id}
                onClick={() => setSelectedPlaylist(playlist)}
                className="group text-left bg-white/5 rounded-lg p-4 hover:bg-white/10 transition-all"
              >
                <div className="relative mb-4">
                  <img
                    src={playlist.imageUrl}
                    alt={playlist.name}
                    className="w-full aspect-square object-cover rounded-md"
                  />
                  <div className="absolute inset-0 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity bg-black/40 rounded-md">
                    <div className="bg-[#1DB954] rounded-full p-3">
                      <Play className="size-6 text-black fill-black" />
                    </div>
                  </div>
                </div>
                <h3 className="mb-1 truncate">{playlist.name}</h3>
                <p className="text-white/60 text-sm">{playlist.tracksCount} tracks</p>
              </button>
            ))}
          </div>
        </div>
      ) : (
        <div className="max-w-7xl">
          {/* Playlist Header */}
          <div className="flex items-end gap-6 mb-8">
            <img
              src={selectedPlaylist.imageUrl}
              alt={selectedPlaylist.name}
              className="size-56 object-cover rounded-lg shadow-2xl"
            />
            <div className="flex-1 pb-4">
              <p className="text-sm mb-2">PLAYLIST</p>
              <h1 className="mb-4">{selectedPlaylist.name}</h1>
              <p className="text-white/60">{selectedPlaylist.tracksCount} tracks</p>
            </div>
          </div>

          {/* Back Button */}
          <button
            onClick={() => setSelectedPlaylist(null)}
            className="text-[#1DB954] hover:underline mb-6"
          >
            ← Back to playlists
          </button>

          {/* Info text */}
          <div className="bg-[#1DB954]/10 border border-[#1DB954]/20 rounded-lg p-4 mb-6">
            <p className="text-sm text-white/80">
              💡 <strong>Tip:</strong> Click on any track to select it, then use bulk actions below. Hover over a track and click the tag icon to add labels individually.
            </p>
          </div>

          {/* Bulk Action Buttons */}
          <div className="flex items-center gap-4 mb-6">
            <Button
              onClick={() => openBulkDialog('selected')}
              disabled={selectedTracks.length === 0}
              className="bg-[#1DB954] hover:bg-[#1ED760] text-black gap-2 disabled:opacity-50 disabled:bg-white/10 disabled:text-white/40"
            >
              <Tag className="size-4" />
              Label Selected ({selectedTracks.length})
            </Button>
            <Button
              onClick={() => openBulkDialog('all')}
              disabled={tracks.length === 0}
              className="bg-white/10 hover:bg-white/20 text-white gap-2 disabled:opacity-50"
            >
              <Tags className="size-4" />
              Label All Tracks
            </Button>
          </div>

          {/* Tracks List */}
          <div className="bg-black/20 rounded-lg">
            <div className="grid grid-cols-[auto,1fr,1fr,auto,auto] gap-4 px-6 py-3 border-b border-white/10 text-white/60 text-sm">
              <div className="w-8">#</div>
              <div>TITLE</div>
              <div>ALBUM</div>
              <div>LABELS</div>
              <div className="w-16 text-right">TIME</div>
            </div>

            <div className="divide-y divide-white/5">
              {selectedPlaylist.tracks.map((track, index) => (
                <TrackItem
                  key={track.id}
                  track={track}
                  index={index}
                  labels={labels}
                  onToggleLabel={onToggleLabel}
                  onToggleSelect={handleToggleSelect}
                  isSelected={selectedTracks.includes(track.id)}
                />
              ))}
            </div>

            {tracks.length === 0 && (
              <div className="text-center py-16 text-white/40">
                <Music className="size-12 mx-auto mb-4 opacity-20" />
                <p>No tracks in this playlist</p>
              </div>
            )}
          </div>

          {/* Bulk Labeling Dialog */}
          <Dialog open={isBulkDialogOpen} onOpenChange={setIsBulkDialogOpen}>
            <DialogContent className="bg-[#282828] border-white/10 text-white max-w-2xl">
              <DialogHeader>
                <DialogTitle>
                  {bulkMode === 'selected' 
                    ? `Bulk Labeling - ${selectedTracks.length} track${selectedTracks.length !== 1 ? 's' : ''} selected`
                    : `Bulk Labeling - All ${tracks.length} tracks`}
                </DialogTitle>
              </DialogHeader>
              
              <div className="space-y-4">
                {/* Search Box */}
                <div className="relative">
                  <Search className="absolute left-3 top-1/2 -translate-y-1/2 size-4 text-white/40" />
                  <Input
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    placeholder="Search labels..."
                    className="bg-[#3E3E3E] border-white/10 text-white placeholder:text-white/40 pl-10"
                  />
                  {searchQuery && (
                    <button
                      onClick={() => setSearchQuery('')}
                      className="absolute right-3 top-1/2 -translate-y-1/2 text-white/40 hover:text-white"
                    >
                      <X className="size-4" />
                    </button>
                  )}
                </div>

                <div className="grid md:grid-cols-2 gap-4">
                  {/* Assign Labels */}
                  <div>
                    <h4 className="mb-3 text-sm text-white/60">Assign Labels ({labelsToAssign.length})</h4>
                    <p className="text-xs text-white/40 mb-2">Click to add label to all selected tracks</p>
                    <div className="space-y-2 max-h-64 overflow-y-auto pr-2 custom-scrollbar">
                      {labelsToAssign.length > 0 ? (
                        labelsToAssign.map((label) => (
                          <button
                            key={label.id}
                            onClick={() => handleBulkLabelAdd(label.id)}
                            className="w-full flex items-center gap-3 p-3 rounded-md hover:bg-white/5 transition-colors border border-white/10"
                          >
                            <div
                              className="size-4 rounded-full"
                              style={{ backgroundColor: label.colorHex}}
                            />
                            <span className="flex-1 text-left">{label.name}</span>
                            <Tag className="size-4 text-white/40" />
                          </button>
                        ))
                      ) : (
                        <p className="text-white/40 text-sm py-4 text-center">
                          {searchQuery ? 'No labels found' : 'All labels already assigned to all tracks'}
                        </p>
                      )}
                    </div>
                  </div>

                  {/* Remove Labels */}
                  <div>
                    <h4 className="mb-3 text-sm text-white/60">Remove Labels ({labelsToRemove.length})</h4>
                    <p className="text-xs text-white/40 mb-2">Click to remove label from all selected tracks</p>
                    <div className="space-y-2 max-h-64 overflow-y-auto pr-2 custom-scrollbar">
                      {labelsToRemove.length > 0 ? (
                        labelsToRemove.map((label) => (
                          <button
                            key={label.id}
                            onClick={() => handleBulkLabelRemove(label.id)}
                            className="w-full flex items-center gap-3 p-3 rounded-md hover:bg-white/5 transition-colors"
                            style={{ backgroundColor: label.colorHex+ '20' }}
                          >
                            <div
                              className="size-4 rounded-full"
                              style={{ backgroundColor: label.colorHex}}
                            />
                            <span className="flex-1 text-left">{label.name}</span>
                            <X className="size-4 text-white/60 hover:text-red-500" />
                          </button>
                        ))
                      ) : (
                        <p className="text-white/40 text-sm py-4 text-center">
                          No labels to remove
                        </p>
                      )}
                    </div>
                  </div>
                </div>
              </div>
            </DialogContent>
          </Dialog>
        </div>
      )}
    </div>
  );
}