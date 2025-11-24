import { Music2, Tag, ListMusic, Search, PlusCircle } from 'lucide-react';
import { Button } from './ui/button';

type LandingPageProps = {
  onLogin: () => void;
};

export function LandingPage({ onLogin }: LandingPageProps) {
  return (
    <div className="min-h-screen bg-gradient-to-b from-[#1DB954] to-[#121212] text-white">
      <div className="container mx-auto px-4 py-16">
        {/* Header */}
        <div className="text-center mb-16">
          <div className="flex items-center justify-center gap-3 mb-6">
            <Music2 className="size-12" />
            <h1 className="text-5xl">Spotify Label Manager</h1>
          </div>
          <p className="text-xl text-white/80 max-w-2xl mx-auto">
            Organize your music like never before. Create custom labels, tag your favorite tracks, and build smart playlists with ease.
          </p>
        </div>

        {/* Features */}
        <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8 mb-16 max-w-6xl mx-auto">
          <div className="bg-white/10 backdrop-blur-sm rounded-lg p-6 hover:bg-white/15 transition-colors">
            <Tag className="size-10 mb-4 text-[#1DB954]" />
            <h3 className="mb-2">Create Labels</h3>
            <p className="text-white/70">
              Design custom labels with colors to categorize your music collection
            </p>
          </div>

          <div className="bg-white/10 backdrop-blur-sm rounded-lg p-6 hover:bg-white/15 transition-colors">
            <ListMusic className="size-10 mb-4 text-[#1DB954]" />
            <h3 className="mb-2">Label Playlists</h3>
            <p className="text-white/70">
              Add labels to tracks from your existing Spotify playlists
            </p>
          </div>

          <div className="bg-white/10 backdrop-blur-sm rounded-lg p-6 hover:bg-white/15 transition-colors">
            <Search className="size-10 mb-4 text-[#1DB954]" />
            <h3 className="mb-2">Search & Tag</h3>
            <p className="text-white/70">
              Find any song on Spotify and instantly add your custom labels
            </p>
          </div>

          <div className="bg-white/10 backdrop-blur-sm rounded-lg p-6 hover:bg-white/15 transition-colors">
            <PlusCircle className="size-10 mb-4 text-[#1DB954]" />
            <h3 className="mb-2">Smart Playlists</h3>
            <p className="text-white/70">
              Generate playlists by combining labels with "match any" or "match all" filters
            </p>
          </div>
        </div>

        {/* CTA */}
        <div className="text-center">
          <Button
            onClick={onLogin}
            className="bg-white text-black hover:bg-white/90 text-lg px-8 py-6 rounded-full gap-3"
          >
            <Music2 className="size-6" />
            Log in with Spotify
          </Button>
          <p className="text-white/60 mt-6 text-sm">
            Connect your Spotify account to start organizing your music
          </p>
        </div>

        {/* Footer */}
        <div className="mt-24 text-center text-white/40 text-sm">
          <p>This app requires Spotify Premium for full functionality</p>
        </div>
      </div>
    </div>
  );
}
