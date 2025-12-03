import { useState } from 'react';
import { Tag, Search, X, Play, Pause } from 'lucide-react';
import { Track, Label } from '../App';
import { Button } from './ui/button';
import { Dialog, DialogContent, DialogHeader, DialogTitle } from './ui/dialog';
import { Input } from './ui/input';

type TrackItemProps = {
  track: Track;
  index: number;
  labels: Label[];
  onToggleLabel: (trackId: string, labelId: string) => void;
  isSelected?: boolean;
  onToggleSelect?: (trackId: string) => void;
};

export function TrackItem({ track, index, labels, onToggleLabel, isSelected = false, onToggleSelect }: TrackItemProps) {
  const [isHovered, setIsHovered] = useState(false);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const [isPlaying, setIsPlaying] = useState(false);

  const trackLabels = labels.filter(label => track.labelsIds.includes(label.id));
  
  // Filter labels based on search query
  const filteredLabels = labels.filter(label =>
    label.name.toLowerCase().includes(searchQuery.toLowerCase())
  );
  
  const assignedLabels = filteredLabels.filter(label => track.labelsIds.includes(label.id));
  const unassignedLabels = filteredLabels.filter(label => !track.labelsIds.includes(label.id));

  const handleTrackClick = () => {
    if (onToggleSelect) {
      onToggleSelect(track.id);
    }
  };

  const handleLabelIconClick = (e: React.MouseEvent) => {
    e.stopPropagation();
    setIsDialogOpen(true);
  };

  const handlePlayPause = (e: React.MouseEvent) => {
    e.stopPropagation();
    setIsPlaying(!isPlaying);
  };

  return (
    <>
      <div
        className={`grid grid-cols-[auto,1fr,1fr,auto,auto] gap-4 px-6 py-3 transition-colors group cursor-pointer ${
          isSelected 
            ? 'bg-[#1DB954]/20 hover:bg-[#1DB954]/30' 
            : 'hover:bg-white/5'
        }`}
        onMouseEnter={() => setIsHovered(true)}
        onMouseLeave={() => setIsHovered(false)}
        onClick={handleTrackClick}
      >
        <div className="w-8 flex items-center text-white/60">
        </div>

        <div className="flex items-center gap-3 min-w-0">
          <img
            src={track.albumImageUrl}
            alt={track.name}
            className="size-10 rounded"
          />
          <div className="min-w-0">
            <div className="truncate">{track.name}</div>
            <div className="text-sm text-white/60 truncate">{track.artist}</div>
          </div>
        </div>

        <div className="flex items-center text-white/60 truncate">
          {track.albumName}
        </div>

        <div className="flex items-center gap-2">
          <div className="flex flex-wrap gap-1">
            {trackLabels.map((label) => (
              <div
                key={label.id}
                className="flex items-center gap-1.5 px-2 py-1 rounded-md text-xs"
                style={{ backgroundColor: label.colorHex}}
              >
                <span className="text-black font-medium">{label.name}</span>
              </div>
            ))}
          </div>

          <Button
            variant="ghost"
            size="sm"
            onClick={handleLabelIconClick}
            className="opacity-0 group-hover:opacity-100 transition-opacity"
          >
            <Tag className="size-4" />
          </Button>
        </div>

        <div className="w-16 flex items-center justify-end gap-4 text-white/60">
          <button
            onClick={handlePlayPause}
            className="opacity-0 group-hover:opacity-100 transition-opacity hover:scale-110 transition-transform"
          >
            {isPlaying ? (
              <div className="bg-white rounded-full p-1.5">
                <Pause className="size-4 text-black fill-black" />
              </div>
            ) : (
              <div className="bg-white rounded-full p-1.5">
                <Play className="size-4 text-black fill-black" />
              </div>
            )}
          </button>
          <span>{track.duration}</span>
        </div>
      </div>

      <Dialog open={isDialogOpen} onOpenChange={setIsDialogOpen}>
        <DialogContent className="bg-[#282828] border-white/10 text-white max-w-2xl">
          <DialogHeader>
            <DialogTitle className="text-left">
              <div className="flex items-center gap-3 pb-2">
                <img
                  src={track.albumImageUrl}
                  alt={track.name}
                  className="size-12 rounded flex-shrink-0"
                />
                <div className="min-w-0 flex-1">
                  <div className="truncate leading-normal">{track.name}</div>
                  <div className="text-sm text-white/60 truncate leading-normal">{track.artist}</div>
                </div>
              </div>
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
              {/* Available Labels - Now on the left */}
              <div>
                <h4 className="mb-3 text-sm text-white/60">Available Labels ({unassignedLabels.length})</h4>
                <div className="space-y-2 max-h-64 overflow-y-auto pr-2 custom-scrollbar">
                  {unassignedLabels.length > 0 ? (
                    unassignedLabels.map((label) => (
                      <button
                        key={label.id}
                        onClick={() => onToggleLabel(track.id, label.id)}
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
                      {searchQuery ? 'No labels found' : 'All labels assigned'}
                    </p>
                  )}
                </div>
              </div>

              {/* Assigned Labels - Now on the right */}
              <div>
                <h4 className="mb-3 text-sm text-white/60">Assigned Labels ({assignedLabels.length})</h4>
                <div className="space-y-2 max-h-64 overflow-y-auto pr-2 custom-scrollbar">
                  {assignedLabels.length > 0 ? (
                    assignedLabels.map((label) => (
                      <button
                        key={label.id}
                        onClick={() => onToggleLabel(track.id, label.id)}
                        className="w-full flex items-center gap-3 p-3 rounded-md hover:bg-white/5 transition-colors"
                        style={{ backgroundColor: label.colorHex+ '20' }}
                      >
                        <div
                          className="size-4 rounded-full"
                          style={{ backgroundColor: label.colorHex }}
                        />
                        <span className="flex-1 text-left">{label.name}</span>
                        <X className="size-4 text-white/60 hover:text-red-500" />
                      </button>
                    ))
                  ) : (
                    <p className="text-white/40 text-sm py-4 text-center">
                      No labels assigned
                    </p>
                  )}
                </div>
              </div>
            </div>
          </div>
        </DialogContent>
      </Dialog>
    </>
  );
}