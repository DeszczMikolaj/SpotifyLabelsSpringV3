import { useState } from 'react';
import { Plus, Trash2 } from 'lucide-react';
import { Button } from './ui/button';
import { Input } from './ui/input';
import { Label as LabelType } from '../App';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogDescription,
} from './ui/dialog';
import { Label } from './ui/label';

type LabelsPanelProps = {
  labels: LabelType[];
  onAddLabel: (name: string, colorHex: string) => void;
  onDeleteLabel: (id: string) => void;
  tracks: any[];
};

const colorOptions = [
  '#1DB954', // Spotify green
  '#1E3A8A', // Blue
  '#9333EA', // Purple
  '#DC2626', // Red
  '#EA580C', // Orange
  '#CA8A04', // Yellow
  '#65A30D', // Lime
  '#0891B2', // Cyan
  '#DB2777', // Pink
  '#78716C', // Gray
];

export function LabelsPanel({ labels, onAddLabel, onDeleteLabel, tracks }: LabelsPanelProps) {
  const [isOpen, setIsOpen] = useState(false);
  const [newLabelName, setNewLabelName] = useState('');
  const [selectedColor, setSelectedColor] = useState(colorOptions[0]);

  const handleCreateLabel = () => {
    if (newLabelName.trim()) {
      onAddLabel(newLabelName, selectedColor);
      setNewLabelName('');
      setSelectedColor(colorOptions[0]);
      setIsOpen(false);
    }
  };

  return (
    <div className="p-8">
      <div className="max-w-6xl">
        <div className="flex items-center justify-between mb-8">
          <div>
            <h2 className="mb-2">Your Labels</h2>
            <p className="text-white/60">Create and manage your track labels</p>
          </div>
          
          <Dialog open={isOpen} onOpenChange={setIsOpen}>
            <DialogTrigger asChild>
              <Button className="bg-[#1DB954] hover:bg-[#1ED760] text-black gap-2">
                <Plus className="size-5" />
                Create Label
              </Button>
            </DialogTrigger>
            <DialogContent className="bg-[#282828] border-white/10 text-white">
              <DialogHeader>
                <DialogTitle>Create New Label</DialogTitle>
              </DialogHeader>
              <DialogDescription className="sr-only">
                Create a new label with a custom name and color
              </DialogDescription>
              <div className="space-y-4 mt-4">
                <div className="space-y-2">
                  <Label htmlFor="label-name">Label Name</Label>
                  <Input
                    id="label-name"
                    value={newLabelName}
                    onChange={(e) => setNewLabelName(e.target.value)}
                    placeholder="Enter label name"
                    className="bg-[#3E3E3E] border-white/10 text-white placeholder:text-white/40"
                    onKeyDown={(e) => e.key === 'Enter' && handleCreateLabel()}
                  />
                </div>
                
                <div className="space-y-2">
                  <Label>Choose Color</Label>
                  <div className="grid grid-cols-5 gap-3">
                    {colorOptions.map((color) => (
                      <button
                        key={color}
                        onClick={() => setSelectedColor(color)}
                        className={`size-12 rounded-full transition-transform hover:scale-110 ${
                          selectedColor === color ? 'ring-2 ring-white ring-offset-2 ring-offset-[#282828]' : ''
                        }`}
                        style={{ backgroundColor: color }}
                      />
                    ))}
                  </div>
                </div>

                <Button
                  onClick={handleCreateLabel}
                  className="w-full bg-[#1DB954] hover:bg-[#1ED760] text-black"
                >
                  Create Label
                </Button>
              </div>
            </DialogContent>
          </Dialog>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
          {labels.map((label) => {
            const tracksCount = tracks.filter(t => t.labels.includes(label.id)).length;
            return (
              <div
                key={label.id}
                className="bg-white/5 rounded-lg p-6 hover:bg-white/10 transition-colors group"
              >
                <div className="flex items-start justify-between mb-4">
                  <div
                    className="size-12 rounded-full"
                    style={{ backgroundColor: label.colorHex }}
                  />
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={() => onDeleteLabel(label.id)}
                    className="opacity-0 group-hover:opacity-100 transition-opacity text-white/60 hover:text-red-500 hover:bg-red-500/10"
                  >
                    <Trash2 className="size-4" />
                  </Button>
                </div>
                <h3 className="mb-1">{label.name}</h3>
                <p className="text-white/40 text-sm">
                  {tracksCount} {tracksCount === 1 ? 'track' : 'tracks'}
                </p>
              </div>
            );
          })}
        </div>

        {labels.length === 0 && (
          <div className="text-center py-16 text-white/40">
            <p>No labels yet. Create your first label to get started!</p>
          </div>
        )}
      </div>
    </div>
  );
}