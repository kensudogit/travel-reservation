'use client';

import { useState } from 'react';
import { useQuery } from 'react-query';
import apiService from '@/services/api';
import { Tour, Destination } from '@/types';
import { format } from 'date-fns';
import { 
  MapPinIcon, 
  CalendarIcon, 
  UsersIcon, 
  CurrencyDollarIcon,
  MagnifyingGlassIcon,
  GlobeAltIcon,
  StarIcon,
  HeartIcon
} from '@heroicons/react/24/outline';
import { Button } from '@/components/ui/Button';
import { Card, CardBody, CardFooter } from '@/components/ui/Card';
import { Input } from '@/components/ui/Input';

/**
 * ホームページコンポーネント
 * 
 * 旅行ツアーの一覧表示と検索機能を提供するメインページです。
 * ツアーの検索、フィルタリング、予約機能を含みます。
 */
export default function HomePage() {
  // 検索キーワードの状態管理
  const [searchTerm, setSearchTerm] = useState('');
  // 選択された目的地の状態管理
  const [selectedDestination, setSelectedDestination] = useState<string>('');
  // 価格範囲の状態管理
  const [priceRange, setPriceRange] = useState<string>('all');

  // 利用可能なツアーの取得（React Query使用）
  const { data: tours, isLoading: toursLoading } = useQuery('availableTours', apiService.getAvailableTours);
  // アクティブな目的地の取得（React Query使用）
  const { data: destinations, isLoading: destinationsLoading } = useQuery('destinations', apiService.getActiveDestinations);

  // ツアーのフィルタリング処理
  const filteredTours = tours?.filter(tour => {
    // 検索キーワードによるフィルタリング（ツアー名と説明文で検索）
    const matchesSearch = tour.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                         tour.description.toLowerCase().includes(searchTerm.toLowerCase());
    // 目的地によるフィルタリング
    const matchesDestination = !selectedDestination || tour.destination.name === selectedDestination;
    // 価格範囲によるフィルタリング
    const matchesPrice = priceRange === 'all' || 
                        (priceRange === 'budget' && tour.price <= 1000) ||
                        (priceRange === 'mid' && tour.price > 1000 && tour.price <= 3000) ||
                        (priceRange === 'luxury' && tour.price > 3000);
    
    return matchesSearch && matchesDestination && matchesPrice;
  });

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-indigo-50">
      {/* ヘッダーセクション */}
      <header className="bg-white shadow-sm border-b border-secondary-200">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center space-x-2">
              <GlobeAltIcon className="h-8 w-8 text-primary-600" />
              <h1 className="text-2xl font-bold text-secondary-900">Travel Explorer</h1>
            </div>
            <nav className="hidden md:flex items-center space-x-6">
              <a href="#" className="text-secondary-600 hover:text-primary-600 transition-colors">ホーム</a>
              <a href="#" className="text-secondary-600 hover:text-primary-600 transition-colors">ツアー</a>
              <a href="#" className="text-secondary-600 hover:text-primary-600 transition-colors">目的地</a>
              <a href="#" className="text-secondary-600 hover:text-primary-600 transition-colors">お問い合わせ</a>
            </nav>
            <Button variant="primary" size="sm">
              ログイン
            </Button>
          </div>
        </div>
      </header>

      {/* ヒーローセクション */}
      <section className="relative bg-gradient-to-r from-primary-600 via-primary-700 to-primary-800 text-white overflow-hidden">
        <div className="absolute inset-0 bg-black opacity-20"></div>
        <div className="relative container mx-auto px-4 py-20">
          <div className="text-center max-w-4xl mx-auto">
            <h2 className="text-5xl md:text-7xl font-bold mb-6 leading-tight">
              世界を
              <span className="text-yellow-300">発見</span>
              しよう
            </h2>
            <p className="text-xl md:text-2xl mb-12 text-primary-100 leading-relaxed">
              一生に一度の思い出を作る、特別な旅行体験をお届けします
            </p>
            
            {/* 検索フォーム */}
            <div className="bg-white rounded-2xl p-6 shadow-2xl max-w-3xl mx-auto">
              <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
                <Input
                  placeholder="ツアーを検索..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  leftIcon={<MagnifyingGlassIcon />}
                  size="lg"
                />
                <select
                  value={selectedDestination}
                  onChange={(e) => setSelectedDestination(e.target.value)}
                  className="px-4 py-3 border border-secondary-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 text-secondary-900"
                >
                  <option value="">すべての目的地</option>
                  {destinations?.map((destination) => (
                    <option key={destination.id} value={destination.name}>
                      {destination.name}
                    </option>
                  ))}
                </select>
                <select
                  value={priceRange}
                  onChange={(e) => setPriceRange(e.target.value)}
                  className="px-4 py-3 border border-secondary-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 text-secondary-900"
                >
                  <option value="all">すべての価格</option>
                  <option value="budget">予算重視 ($1000以下)</option>
                  <option value="mid">中級 ($1000-$3000)</option>
                  <option value="luxury">高級 ($3000以上)</option>
                </select>
                <Button size="lg" className="w-full">
                  検索
                </Button>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* 統計セクション */}
      <section className="py-16 bg-white">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-2 md:grid-cols-4 gap-8 text-center">
            <div>
              <div className="text-3xl font-bold text-primary-600 mb-2">500+</div>
              <div className="text-secondary-600">ツアー</div>
            </div>
            <div>
              <div className="text-3xl font-bold text-primary-600 mb-2">50+</div>
              <div className="text-secondary-600">目的地</div>
            </div>
            <div>
              <div className="text-3xl font-bold text-primary-600 mb-2">10K+</div>
              <div className="text-secondary-600">満足したお客様</div>
            </div>
            <div>
              <div className="text-3xl font-bold text-primary-600 mb-2">24/7</div>
              <div className="text-secondary-600">サポート</div>
            </div>
          </div>
        </div>
      </section>

      {/* ツアーセクション */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <div className="text-center mb-12">
            <h3 className="text-4xl font-bold text-secondary-900 mb-4">
              おすすめツアー
            </h3>
            <p className="text-xl text-secondary-600 max-w-2xl mx-auto">
              厳選された人気ツアーをご紹介します。あなたに最適な旅行体験を見つけてください。
            </p>
          </div>
          
          {toursLoading ? (
            <div className="text-center py-12">
              <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600 mx-auto"></div>
              <p className="mt-4 text-secondary-600">ツアーを読み込み中...</p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
              {filteredTours?.map((tour) => (
                <TourCard key={tour.id} tour={tour} />
              ))}
            </div>
          )}

          {filteredTours?.length === 0 && !toursLoading && (
            <div className="text-center py-12">
              <div className="text-secondary-400 mb-4">
                <MagnifyingGlassIcon className="h-16 w-16 mx-auto" />
              </div>
              <p className="text-secondary-600 text-lg">条件に一致するツアーが見つかりませんでした。</p>
              <p className="text-secondary-500 mt-2">検索条件を変更してお試しください。</p>
            </div>
          )}
        </div>
      </section>

      {/* フッター */}
      <footer className="bg-secondary-900 text-white py-12">
        <div className="container mx-auto px-4">
          <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
            <div>
              <div className="flex items-center space-x-2 mb-4">
                <GlobeAltIcon className="h-6 w-6 text-primary-400" />
                <span className="text-xl font-bold">Travel Explorer</span>
              </div>
              <p className="text-secondary-300">
                世界を発見する、特別な旅行体験をお届けします。
              </p>
            </div>
            <div>
              <h4 className="font-semibold mb-4">ツアー</h4>
              <ul className="space-y-2 text-secondary-300">
                <li><a href="#" className="hover:text-white transition-colors">人気ツアー</a></li>
                <li><a href="#" className="hover:text-white transition-colors">新着ツアー</a></li>
                <li><a href="#" className="hover:text-white transition-colors">季節限定</a></li>
              </ul>
            </div>
            <div>
              <h4 className="font-semibold mb-4">サポート</h4>
              <ul className="space-y-2 text-secondary-300">
                <li><a href="#" className="hover:text-white transition-colors">お問い合わせ</a></li>
                <li><a href="#" className="hover:text-white transition-colors">よくある質問</a></li>
                <li><a href="#" className="hover:text-white transition-colors">予約ガイド</a></li>
              </ul>
            </div>
            <div>
              <h4 className="font-semibold mb-4">会社情報</h4>
              <ul className="space-y-2 text-secondary-300">
                <li><a href="#" className="hover:text-white transition-colors">会社概要</a></li>
                <li><a href="#" className="hover:text-white transition-colors">プライバシーポリシー</a></li>
                <li><a href="#" className="hover:text-white transition-colors">利用規約</a></li>
              </ul>
            </div>
          </div>
          <div className="border-t border-secondary-800 mt-8 pt-8 text-center text-secondary-400">
            <p>&copy; 2024 Travel Explorer. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}

/**
 * ツアーカードコンポーネント
 * 
 * 個別のツアー情報を表示するカード形式のコンポーネントです。
 * ツアーの画像、詳細情報、価格、予約ボタンを含みます。
 * 
 * @param tour 表示するツアー情報
 */
function TourCard({ tour }: { tour: Tour }) {
  const [isFavorite, setIsFavorite] = useState(false);

  return (
    <Card hover shadow="lg" className="group">
      {/* ツアー画像の表示（存在する場合のみ） */}
      <div className="relative overflow-hidden">
        {tour.imageUrl ? (
          <img
            src={tour.imageUrl}
            alt={tour.name}
            className="w-full h-48 object-cover group-hover:scale-105 transition-transform duration-300"
          />
        ) : (
          <div className="w-full h-48 bg-gradient-to-br from-primary-100 to-primary-200 flex items-center justify-center">
            <GlobeAltIcon className="h-16 w-16 text-primary-400" />
          </div>
        )}
        
        {/* お気に入りボタン */}
        <button
          onClick={() => setIsFavorite(!isFavorite)}
          className="absolute top-4 right-4 p-2 bg-white rounded-full shadow-md hover:shadow-lg transition-shadow"
        >
          <HeartIcon 
            className={`h-5 w-5 ${isFavorite ? 'text-red-500 fill-current' : 'text-secondary-400'}`} 
          />
        </button>
        
        {/* 目的地バッジ */}
        <div className="absolute top-4 left-4">
          <span className="bg-primary-600 text-white px-3 py-1 rounded-full text-sm font-medium">
            {tour.destination.name}
          </span>
        </div>
      </div>

      <CardBody>
        {/* ツアー名と評価 */}
        <div className="flex items-start justify-between mb-3">
          <h3 className="text-xl font-semibold text-secondary-900 group-hover:text-primary-600 transition-colors">
            {tour.name}
          </h3>
          <div className="flex items-center space-x-1">
            <StarIcon className="h-4 w-4 text-yellow-400 fill-current" />
            <span className="text-sm text-secondary-600">4.8</span>
          </div>
        </div>
        
        {/* ツアー説明 */}
        <p className="text-secondary-600 mb-4 line-clamp-3">
          {tour.description}
        </p>
        
        {/* ツアー詳細情報 */}
        <div className="space-y-3 mb-4">
          <div className="flex items-center gap-2">
            <CalendarIcon className="h-4 w-4 text-secondary-500" />
            <span className="text-sm text-secondary-600">
              {format(new Date(tour.startDate), 'yyyy年MM月dd日')} - {format(new Date(tour.endDate), 'MM月dd日')}
            </span>
          </div>
          
          <div className="flex items-center gap-2">
            <UsersIcon className="h-4 w-4 text-secondary-500" />
            <span className="text-sm text-secondary-600">
              {tour.currentCapacity} 席空きあり
            </span>
          </div>
          
          <div className="flex items-center gap-2">
            <MapPinIcon className="h-4 w-4 text-secondary-500" />
            <span className="text-sm text-secondary-600">
              {tour.destination.name}
            </span>
          </div>
        </div>
      </CardBody>

      <CardFooter>
        <div className="flex items-center justify-between w-full">
          <div>
            <span className="text-2xl font-bold text-primary-600">
              ¥{tour.price.toLocaleString()}
            </span>
            <span className="text-sm text-secondary-500 ml-1">/人</span>
          </div>
          <Button variant="primary" size="sm">
            予約する
          </Button>
        </div>
             </CardFooter>
     </Card>
   );
} 